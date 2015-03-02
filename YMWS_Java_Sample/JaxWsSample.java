import com.yahoo.mail.*;
import BrowserBasedAuthManager;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.xml.ws.Holder;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.util.*;
import java.net.URLEncoder;
import java.math.BigInteger;

/**
 * Download JAX-WS (https://jax-ws.dev.java.net/2.1.2rc1/).
 * <p/>
 * Run the WSDL importer: wsimport.sh -extension -s src -p com.yahoo.mail http://mail.yahooapis.com/ws/mail/v1.1/wsdl
 * <p/>
 * Add the JAX-WS and JAXB libraries to your classpath as necessary.
 */
public class JaxWsSample {

    // Enter your application ID here.
    private static String appid = "your application ID here";

    // Enter your shared secret here.
    private static String secret = "your shared secret here";

    /**
     * Simple driver program that shows how to call the Yahoo! Mail Web Service
     * from Java using Axis2.
     */
    public static void main(String[] args) throws Exception {
        // Enter the user token here. Normally this would be passed in, but for the convenience of running this
        // code sample standalone, I've hardcoded it here.
        String token = "user token here";

        // Instantiate the auth manager and set it up with the application ID, shared secret and the user token.
        // You'll have to get the token through the normal Browser Based Authentication flow.
        BrowserBasedAuthManager authManager = new BrowserBasedAuthManager(appid, secret);
        authManager.setToken(token);

        // Trigger the fetching of the WSSID and the "Y" cookie from Yahoo!.
        authManager.authenticate();

        // Instantiate the SOAP proxy. Override the default endpoint so the application ID and the WSSID
        // can be added to the URL.
        Ymws service = new Ymws();
        YmwsPortType stub = service.getYmws();

        Map<String, Object> requestContext = ((BindingProvider) stub).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                "http://mail.yahooapis.com/ws/mail/v1.1/soap?appid=" +
                        URLEncoder.encode(appid, "UTF-8") + "&wssid=" +
                        URLEncoder.encode(authManager.getWssid(), "UTF-8"));
        Map<String, List<String>> cookies = new HashMap<String, List<String>>();
        cookies.put("Cookie", Arrays.asList(authManager.getCookie()));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, cookies);

        // GetUserData, pulls user preferences and account settings.
        UserData userData = stub.getUserData();
        System.out.println(String.format("Loaded user data for %1$s",
                userData.getUserSendPref().getDefaultFromName()));

        // SetUserData, sets user preferences and some account settings. This code
        // reverses the current setting of the "use rich text for compose" preference.
        System.out.println(String.format("Old useRichText setting = %1$s",
                userData.getUserUIPref().getUseRichText().value()));
        SetData setUserData = new SetData();
        setUserData.setUserUIPref(new SetUIPref());
        if (userData.getUserUIPref().getUseRichText() == UseRichText.PLAIN) {
            setUserData.getUserUIPref().setUseRichText(UseRichText.DYNAMIC);
        }
        else {
            setUserData.getUserUIPref().setUseRichText(UseRichText.PLAIN);
        }
        stub.setUserData(setUserData);
        userData = stub.getUserData();
        System.out.println(String.format("New useRichText setting = %1$s",
                userData.getUserUIPref().getUseRichText().value()));

        // ListFolders, gets a list of the folders present in the mailbox
        // and the message counts for each folder.
        Holder<Long> numberOfFoldersHolder = new Holder<Long>();
        Holder<List<FolderData>> foldersHolder = new Holder<List<FolderData>>();
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        System.out.println(String.format("%1$s folders present.",
                numberOfFoldersHolder.value));
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            System.out.println(String.format("    %1$s - %2$d (%3$d)", folderData.getFolderInfo().getName(),
                    folderData.getTotal().longValue(), folderData.getUnread().longValue()));
        }

        // CreateFolder, creates a new folder given a name. Returns the
        // folder ID of the newly created folder.
        Fid createdFolder = stub.createFolder("Axis2 Test Folder");
        System.out.println(String.format("Creating folder %1$s", createdFolder.getName()));
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            if (folderData.getFolderInfo().getFid().equals(createdFolder.getFid())) {
                System.out.println(String.format("Folder %1$s created.", folderData.getFolderInfo().getName()));
            }
        }

        // RenameFolder, renames a folder given a folder ID and a new foldername. Returns
        // the folder ID of the newly renamed folder.
        Fid renamedFolder = stub.renameFolder(createdFolder.getFid(), "Renamed Axis2 Test Folder");
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        FolderData testFolder = null;
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            if (folderData.getFolderInfo().getFid().equals(renamedFolder.getFid())) {
                testFolder = folderData;
                System.out.println(String.format("Folder %1$s renamed to %2$s",
                        createdFolder.getName(), folderData.getFolderInfo().getName()));
            }
        }

        // SaveRawMessage, saves a raw message to the mailbox. This can be useful for
        // doing mailbox imports from other mail systems. The parameters include
        // the folder ID of the folder being saved to and the base64 encoded text of
        // the message being saved.
        String messagetext = "From Ryan Kennedy Mon Mar 19 09:18:08 2007\nReceived: from [68.142.200.247] by web32309.mail.mud.yahoo.com via HTTP; Mon, 19 Mar 2007 09:18:08 PDT\nDate: Mon, 19 Mar 2007 09:18:08 -0700 (PDT)\nFrom: Ryan Kennedy <rckenned@yahoo-inc.com>\nSubject: Save message test\nTo: Ryan Kennedy <rckenned@yahoo-inc.com>\nMIME-Version: 1.0\nContent-Type: text/plain; charset=ascii\nContent-Length: 42\n\nThis is a test raw message from yourself.";
        String base64text = Base64.encode(messagetext.getBytes("us-ascii"));
        String rawMessageMid = stub.saveRawMessage(renamedFolder.getFid(), base64text);
        System.out.println(String.format("Saved raw message, mid = %1$s.", rawMessageMid));
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            if (folderData.getFolderInfo().getFid().equals(renamedFolder.getFid())) {
                testFolder = folderData;
            }
        }

        // GetMessage, gets one or more messages given a folder ID and a list of message
        // IDs.
        List<String> midList = new ArrayList<String>();
        midList.add(rawMessageMid);
        Holder<BigInteger> totalHolder = new Holder<BigInteger>();
        Holder<FolderData> folderHolder = new Holder<FolderData>();
        Holder<List<Message>> messageHolder = new Holder<List<Message>>();
        Holder<List<Header>> headerHolder = new Holder<List<Header>>();
        Holder<List<ErrorCode>> errorCodeHolder = new Holder<List<ErrorCode>>();
        stub.getMessage(null, renamedFolder.getFid(), midList, null, null, totalHolder, folderHolder, messageHolder, headerHolder, errorCodeHolder);
        for (Iterator<Message> iterator = messageHolder.value.iterator(); iterator.hasNext();) {
            Message message = iterator.next();
            System.out.println(String.format("Fetched message, subject = %1$s.", message.getSubject().getValue()));
            for (Iterator<MessagePart> iterator1 = message.getPart().iterator(); iterator1.hasNext();) {
                MessagePart messagePart = iterator1.next();
                System.out.println(String.format("    Part id = %1$s, Content-Type = %2$s/%3$s, text size = %4$s.",
                        messagePart.getPartId(), messagePart.getType(), messagePart.getSubtype(),
                        messagePart.getSize()));

            }

        }

        // GetMessageRawHeader, gets the raw message headers for one or more messages
        // given a folder ID and a list of message IDs.
        GetMessageRawHeader getRawHeader = new GetMessageRawHeader();
        getRawHeader.setFid(renamedFolder.getFid());
        getRawHeader.getMid().add(rawMessageMid);
        GetMessageRawHeaderResponse rawHeaderResponse = stub.getMessageRawHeader(getRawHeader);
        System.out.println(String.format("Fetched %1$s raw headers.", rawHeaderResponse.getTotal()));

        // FlagMessages, updates message flags given a folder ID and a list of message IDs.
        // In this case, the previously saved message will be changed from unread to read.
        System.out.println(String.format("Folder %1$s has %2$d unread messages before flagging.",
                renamedFolder.getName(), testFolder.getUnread()));
        midList = new ArrayList<String>();
        midList.add(rawMessageMid);
        SetFlag setFlag = new SetFlag();
        setFlag.setRead(1);
        stub.flagMessages(renamedFolder.getFid(), midList, setFlag, null);
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            if (folderData.getFolderInfo().getFid().equals(renamedFolder.getFid())) {
                testFolder = folderData;
                System.out.println(String.format("Folder %1$s has %2$d unread messages after flagging.",
                        testFolder.getFolderInfo().getName(), testFolder.getUnread().longValue()));
            }
        }

        // ListMessages, lists the messages in a given folder.
        ListMessages listMessagesRequest = new ListMessages();
        listMessagesRequest.setFid(renamedFolder.getFid());
        listMessagesRequest.setStartMid(new BigInteger("0"));
        listMessagesRequest.setNumMid(new BigInteger("10"));
        ListMessagesResponse listMessagesResponse = stub.listMessages(listMessagesRequest);
        System.out.println(String.format("Listed folder %1$s, found %2$d message IDs.",
                listMessagesResponse.getFolder().getName(),
                (listMessagesResponse.getMid() != null) ? listMessagesResponse.getMid().size() : 0));

        // ListMessagesFromIds, loads message descriptors from a given folder using
        // a list of message IDs.
        midList = new ArrayList<String>();
        midList.add(rawMessageMid);
        folderHolder = new Holder<FolderData>();
        Holder<List<MessageInfo>> messageInfoHolder = new Holder<List<MessageInfo>>();
        stub.listMessagesFromIds(renamedFolder.getFid(), midList, null, folderHolder, messageInfoHolder);
        System.out.println(String.format("Listed %1$d message infos from IDs in folder %2$s.",
                messageInfoHolder.value.size(),
                folderHolder.value.getFolderInfo().getName()));

        // MoveMessages, moves messages from a source folder to a destination folder
        // given both folder IDs and a list of message IDs.
        Holder<List<String>> midListHolder = new Holder<List<String>>();
        midListHolder.value = new ArrayList<String>();
        midListHolder.value.add(rawMessageMid);
        Holder<FolderData> sourceFolderHolder = new Holder<FolderData>();
        Holder<FolderData> destinationFolderHolder = new Holder<FolderData>();
        stub.moveMessages(renamedFolder.getFid(), "Trash", midListHolder, null, sourceFolderHolder, destinationFolderHolder, errorCodeHolder);
        System.out.println(String.format("Moved message %1$s:%2$s to %3$s:%4$s.",
                testFolder.getFolderInfo().getName(), rawMessageMid,
                "Trash", midListHolder.value.get(0)));
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            if (folderData.getFolderInfo().getFid().equals(renamedFolder.getFid())) {
                testFolder = folderData;
            }
        }

        // DeleteMessages, deletes messages from a folder given a folder ID
        // and a list of message IDs.
        midList = new ArrayList<String>();
        midList.add(midListHolder.value.get(0));
        List<ErrorCode> errors = stub.deleteMessages("Trash", midList, null);
        System.out.println(String.format("Deleted message, %1$d errors reported.",
                errors != null ? errors.size() : 0));

        // SearchMessages, searches the mailbox using a given query. In this case,
        // search for messages containing the subject used in the earlier call to
        // SaveMessage. The response to SearchMessages is identical to the response
        // for ListMessages.
        SearchMessages searchMessages = new SearchMessages();
        searchMessages.setSearch(new SearchQuery());
        searchMessages.getSearch().setQuery("subject:\"Save message test\"");
        searchMessages.setStartInfo(new BigInteger("0"));
        searchMessages.setNumInfo(new BigInteger("100"));
        ListMessagesResponse searchResults = stub.searchMessages(searchMessages);
        System.out.println(String.format("Search for '%1$s' found %2$d messages.",
                searchMessages.getSearch().getQuery(), searchResults.getMessageInfo().size()));

        // FetchExternalMail, fetches mail from configured external mail accounts.
        // This code checks first to make sure there are configured external accounts.
        if (userData.getExternalAccounts().getExtAccount().size() > 0) {
            // Found at least one external account, fetch the first.
            ExternalAccount externalAccount = new ExternalAccount();
            externalAccount.setProtocol("pop3");
            externalAccount.setServer(userData.getExternalAccounts().getExtAccount().get(0).getServer());
            externalAccount.setUserName(userData.getExternalAccounts().getExtAccount().get(0).getUsername());
            List<ExternalAccount> externalAccounts = new ArrayList<ExternalAccount>();
            externalAccounts.add(externalAccount);
            List<FetchResult> fetchResults = stub.fetchExternalMail(externalAccounts);
            System.out.println(String.format("Fetched %1$d messages from %2$s@%3$s.",
                    fetchResults.get(0).getNumFetched(), externalAccount.getUserName(),
                    externalAccount.getServer()));
        }
        else {
            // No external accounts found.
            System.out.println("No external accounts configured, skipping FetchExternalMail().");
        }

        // EmptyFolder, empties a given folder of all messages. Messages are
        // removed completely. They are not moved to the trash.
        System.out.println(String.format("Folder %1$s has %2$d messages, emptying",
                renamedFolder.getName(), testFolder.getTotal()));
        stub.emptyFolder(renamedFolder.getFid());
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            if (folderData.getFolderInfo().getFid().equals(renamedFolder.getFid())) {
                testFolder = folderData;
                System.out.println(String.format("Folder %1$s has %2$d messages after emptying.",
                        testFolder.getFolderInfo().getName(), testFolder.getTotal().longValue()));
            }
        }

        // RemoveFolder, removes a given folder.
        stub.removeFolder(renamedFolder.getFid());
        stub.listFolders(Boolean.FALSE, numberOfFoldersHolder, foldersHolder);
        for (Iterator<FolderData> iterator = foldersHolder.value.iterator(); iterator.hasNext();) {
            FolderData folderData = iterator.next();
            if (folderData.getFolderInfo().getFid().equals(renamedFolder.getFid())) {
                System.out.println(String.format("Folder %1$s wasn't removed...oops.", renamedFolder.getName()));
            }
        }

    }
}