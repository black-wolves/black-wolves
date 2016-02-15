package com.blackwolves.mail.util;

import java.util.UUID;

public interface Constant {

	public static final String ROUTE = "/var/www/";
	public static final String ROUTE_LOGS_ERROR = "/var/www/logs/errors/";
	public static final String EMPTY_STRING = "";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String BLANK_SPACE = " ";

	public interface Yahoo{
		public static final String IMAP_YAHOO = "imap.mail.yahoo.com";
		public static final int IMAP_PORT = 993;
		public static final String PICKUP_ROUTE = "/ramcache/pmta/pickup/";
		public static final String BLACKWOLVES_ROUTE = "/var/www/bodies/";
		public static final String INBOX = "Inbox";
		public static final String SPAM = "Bulk Mail";
		public static final String BODIES = "Bodies";

		public static final String HOST = "smtp.mail.yahoo.com";
		public static final String PORT = "587";
		public static final String CONTENT_TYPE = "text/html; charset=utf-8";
		public static final String CONTENT_TRANSFER_ENCODING = "7bit";
	}
	
	public static final String[] senderEmails = {
												"pedro.vicco@austroyed.info,wolf2015.2",
												"ykfzfg@austroyed.info,wolf2015.1",
												"pbjpbunb@austroyed.info,wolf2015.1",
												"dlvszwwqxsg@austroyed.info,wolf2015.2",
												"qldqlrlanpt@austroyed.info,wolf2015.2",
												"ewcppqfts@austroyed.info,wolf2015.",
												"evuhoiheske@austroyed.info,wolf2015.",
												"tnaup@austroyed.info,wolf2015.",
												"frcgevpgoeo@austroyed.info,wolf2015.",
												"smbidnklmz@austroyed.info,wolf2015.",
												"sddxjaiye@austroyed.info,wolf2015.",
												"icdzwvn@austroyed.info,wolf2015.",
												"kbbivek@austroyed.info,wolf2015.",
												"vwctiphvxo@austroyed.info,wolf2015.",
												"lxsgknxykhq@austroyed.info,wolf2015."
												};
	
	public static final String[] contactEmails = {
												"offers-1@austroyed.info,wolf2015.",
												"offers-2@austroyed.info,wolf2015.",
												"offers-3@austroyed.info,wolf2015.",
												"offers-4@austroyed.info,wolf2015.",
												"offers-5@austroyed.info,wolf2015."
												};
	
	public interface offer_804{
		public static final String[] froms = {
											"The HARP Lenders",
											"The Harp Lenders",
											"TheHarpLenders",
											"TheHarpLenders Partner",
											"TheHarpLenders Offer",
											"TheHarpLenders Website",
											"Harp Lenders Direct",
											"harplendersdirect",
											"harp lenders direct",
											"harp.lenders.direct",
											"harplenders-direct",
											"harp-lenders-direct"
											};
		
		public static final String[] subjects = {
												"Presidents Refinance Program",
												"HARP Extended into 2016. Start Saving on Your Mortgage Now.",
												"2016 HARP Program Extended. Get Dramatic Mortgage Savings",
												"Save Thousands on Your Mortgage. Learn More about HARP.",
												"Save Thousands on Your Mortgage with the Home Affordable Refinance Program.",
												"HARP 2.0 Refinance Program Extended Until 2016 - Act Before Rates Go Up",
												"President Announces HARP Extension! Respond",
												"Licensed Lenders for HARP 2.0 Program await you!",
												"What Is HARP? Info here",
												"HARP - Why refinance? we can help!",
												"Obama waived Minimum Credit Score requirements for Refinancing. Take advantage while you can!",
												"Obama Announces the HARP Program. Learn How You Can Save Thousands a Year" +
												"HARP 2.0 Extended!",
												"Refinance Today!",
												"Mortgage Underwater?",
												"Mortgage Underwater? Obama's HARP Program Extended!",
												"Refinance with Obama's Home Affordable Refinance Program",
												"Refinance Your Underwater Mortgage with Obama's Home Affordable Refinance Program",
												"Struggling to Pay Your Mortgage? Refinance Today!",
												"Struggling to Pay Your Mortgage? Refinance with HARP today!",
												"See if You Qualify For The Obama HARP 2.0 Plan",
												"How Much Will YOU save with Obama's HARP 2.0?",
												"Refinance and Save With Government HARP 2.0",
												"Obama's HARP 2.0 Refinance Program",
												"2016 HARP Program Extended. Get Dramatic Mortgage Savings",
												"HARP Extended into 2016. Qualify To Save On Your Mortgage",
												"HARP Savings Are still Available. Lower Your Mortgage Payment Today",
												"HARP Extended into 2016. Start Saving on Your Mortgage Now.",
												"homeowners suffering during the mortgage crisis"
												};
		
			public static final String[] bodies = {
												"<html>" +
												"<body bgcolor=\"#FFFFFF\"><br />" +
												"<h1><center><a target=\"_blank\" href=\"http://collowed.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e8211ad6f629f9714242293389c2726788f13b45bf9870d91c312a7d46579d0b70\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
												"<map name=\"wruqu\"><area shape=\"rect\" coords=\"4,2,2743,2956\" target=\"_blank\" href=\"http://collowed.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e8211ad6f629f9714242293389c2726788f13b45bf9870d91c312a7d46579d0b70\"></map>" +
												"<map name=\"cihrl\"><area shape=\"rect\" coords=\"4,1,2004,2696\" target=\"_blank\" href=\"http://collowed.info/c61cf1f7683bfef7b07d82c668cbaa36b0bd5052fade62e4d51a44e21886d8eb472451cada12dd0d9c4b67e7492a738b7748fca6c094e5fd7f03de30133bd673\"></map>" +
												"<table height=\"351\" width=\"407\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
												"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://collowed.info/337a6d0c1a8c45dbd31e3a959d0d8c276db43f2e1ea077256525153a52c15b6037101e10d4045312244b9cf68d3c21a73d83c6f08d07721fc8aa91499f49f6ce\" usemap=\"#wruqu\"></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://collowed.info/312a8b2346fbc0a25d0cae29f5a71eb8803d3548da8ad03a7fdfb84a56809e01ef6e653f336371c9cf4832cdd7c5dbab7d73e5f30fa4bdc221b2b879287c4604\" usemap=\"#cihrl\"></td></tr></table><br />" +
												"</body>" +
												"</html>",
			
												"<html>" +
												"<body bgcolor=\"#FFFFFF\"><br />" +
												"<h1><center><a target=\"_blank\" href=\"http://catholocausing.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63a30a2386251631db9331eb56a89afc5fc338440bed61e93a62ebaff59d6dca9d\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
												"<map name=\"sjzkg\"><area shape=\"rect\" coords=\"0,0,2102,2708\" target=\"_blank\" href=\"http://catholocausing.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63a30a2386251631db9331eb56a89afc5fc338440bed61e93a62ebaff59d6dca9d\"></map>" +
												"<map name=\"uvwba\"><area shape=\"rect\" coords=\"1,3,2761,2404\" target=\"_blank\" href=\"http://catholocausing.info/db0b993e7b373a0370b0cca089f680f4bcc63a5b865c9513a081bd77b86d37d2416764f76ce52f3717bca4951f14b11bb3064b296401610b44d7b46b77dff5d9\"></map>" +
												"<table height=\"345\" width=\"499\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
												"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://catholocausing.info/5dd7f803c0270767fad0fa43490a2176e23f3b83fbd920432d7fa137a7fdcc152c85b2adaf87efbf10ce0a589aea6b0cf6c566d7bbf85b3c396bd657c9e5fcb6\" usemap=\"#sjzkg\"></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://catholocausing.info/108de14473fbd0f11f4b3f4a6e7eba75c9845958b35126ab3249c0b2741b52c8ab57830548b6ed6bd31ca662ac39791bb39a76b032611ea90a5a4fbb8271ea4e\" usemap=\"#uvwba\"></td></tr></table><br />" +
												"</body>" +
												"</html>",

												"<html>" +
												"<body bgcolor=\"#FFFFFF\"><br />" +
												"<h1><center><a target=\"_blank\" href=\"http://geneveral.info/76143020d90cca2d29091aa4c4feb6889b90c7c606151d0c2fa46ff07381e7960326a5e261cefeb2c21250d67ca715634e1d5164b19bc21bb8949eb7eeb41663\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
												"<map name=\"vvupk\"><area shape=\"rect\" coords=\"1,1,2336,2862\" target=\"_blank\" href=\"http://geneveral.info/76143020d90cca2d29091aa4c4feb6889b90c7c606151d0c2fa46ff07381e7960326a5e261cefeb2c21250d67ca715634e1d5164b19bc21bb8949eb7eeb41663\"></map>" +
												"<map name=\"jszbv\"><area shape=\"rect\" coords=\"4,3,2482,2383\" target=\"_blank\" href=\"http://geneveral.info/c953465b5b3d5b1547a44ffec0cdc59f592db6c373933392bd00b2091b69f64e9e5d84d25ede03bd136a3772669950b728d41a7a5826b92dbaf6495ec862d710\"></map>" +
												"<table height=\"380\" width=\"487\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
												"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://geneveral.info/c910276c3f93d1119e9c9d5a289525a93dea8e8d205004eb468aab2b0872ee5cfa6d3efa12c202bc481e42bc072a4fde850c3ce3900c74ed679978ba060effaf\" usemap=\"#vvupk\"></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://geneveral.info/ccd73a49d288c921140d058fb14de385b853fcead0cc25c58edc2eedae4c155455e48721b74ef81211b3c9519b24ebdb135ac099d96fff305abc1e47ccf6fb3f\" usemap=\"#jszbv\"></td></tr></table><br />" +
												"</body>" +
												"</html>",

												"<html>" +
												"<body bgcolor=\"#FFFFFF\"><br />" +
												"<h1><center><a target=\"_blank\" href=\"http://calculum.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e88d5b64c6dc50539578748c3625a255b8d2265bcd9a03325f785562e4e33d3d53\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
												"<map name=\"explr\"><area shape=\"rect\" coords=\"0,1,2150,2746\" target=\"_blank\" href=\"http://calculum.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e88d5b64c6dc50539578748c3625a255b8d2265bcd9a03325f785562e4e33d3d53\"></map>" +
												"<map name=\"tcshz\"><area shape=\"rect\" coords=\"1,1,2348,2950\" target=\"_blank\" href=\"http://calculum.info/c61cf1f7683bfef7b07d82c668cbaa36b0bd5052fade62e4d51a44e21886d8eb5367b11b9bf1f0821ad0dcc020c6a1c0a2764e95d6ceacb09045af832ef8f1c2\"></map>" +
												"<table height=\"344\" width=\"488\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
												"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://calculum.info/337a6d0c1a8c45dbd31e3a959d0d8c276db43f2e1ea077256525153a52c15b6082c44aca48bde1c325d78519032e808745e8102c5dfab7ea61f6877b4899ad5b\" usemap=\"#explr\"></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://calculum.info/312a8b2346fbc0a25d0cae29f5a71eb8803d3548da8ad03a7fdfb84a56809e0148e1e2e97b59e34cf78807c0e80593246437f1c37ee4dcbb62e3ebf5d0703e97\" usemap=\"#tcshz\"></td></tr></table><br />" +
												"</body>" +
												"</html>",

												"<html>" +
												"<body bgcolor=\"#FFFFFF\"><br />" +
												"<h1><center><a target=\"_blank\" href=\"http://tichess.info/06cff3d440bf848c815f5e84ac969c9ea92ba6e0cdc94e3603f2c800b5ce1a45d0facef77781e2309f66b14bc5b64a3ff34b5022d0f7eaddc0e59dd431ed88ba\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
												"<map name=\"pzhzg\"><area shape=\"rect\" coords=\"0,1,2890,2846\" target=\"_blank\" href=\"http://tichess.info/06cff3d440bf848c815f5e84ac969c9ea92ba6e0cdc94e3603f2c800b5ce1a45d0facef77781e2309f66b14bc5b64a3ff34b5022d0f7eaddc0e59dd431ed88ba\"></map>" +
												"<map name=\"yuyrk\"><area shape=\"rect\" coords=\"2,4,2167,2434\" target=\"_blank\" href=\"http://tichess.info/6a07d06a4a74b6adb2d02b546756080d5ca7c134af97339d7d011f41c871b0b582b329b21d05350a8823ff9ee396d5db9e74fc63dd8d94b4eb693e3e48d24695\"></map>" +
												"<table height=\"307\" width=\"482\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
												"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://tichess.info/93826849aaf60ca8062fb1906c48a5c593c52e3e5eaf0706ba4b090f530573e01cc382c77596db851f399fdbdb4bdcd1e7358ad2cfeddec66bef52ea92366194\" usemap=\"#pzhzg\"></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://tichess.info/57c8c0fe4d8fa827bdc18aa50b48b8c5119cc92bec88161149da47157229daa7ad7566ec9d5330ea3738b39cc75a152d2049b249658ee1baba765dd5b8580349\" usemap=\"#yuyrk\"></td></tr></table><br />" +
												"</body>" +
												"</html>",

												"<html>" +
												"<body bgcolor=\"#FFFFFF\"><br />" +
												"<h1><center><a target=\"_blank\" href=\"http://localiform.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63ec402a76a0426699f2addd10bbc820b6bbc7d42338f5f8ac5562d30c302571e3\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
												"<map name=\"imfme\"><area shape=\"rect\" coords=\"2,3,2814,2646\" target=\"_blank\" href=\"http://localiform.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63ec402a76a0426699f2addd10bbc820b6bbc7d42338f5f8ac5562d30c302571e3\"></map>" +
												"<map name=\"fbtpf\"><area shape=\"rect\" coords=\"0,0,2579,2161\" target=\"_blank\" href=\"http://localiform.info/db0b993e7b373a0370b0cca089f680f4bcc63a5b865c9513a081bd77b86d37d2c82474e8ea1f576d377aff49bed522356aa7ab7c5963573c32e0e64fac05b95f\"></map>" +
												"<table height=\"374\" width=\"446\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
												"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://localiform.info/5dd7f803c0270767fad0fa43490a2176e23f3b83fbd920432d7fa137a7fdcc15c37ce5d6b74d7e90d2f45b7ece749dadc4f54568929fbdbdd31ce1b1eb52c000\" usemap=\"#imfme\"></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://localiform.info/108de14473fbd0f11f4b3f4a6e7eba75c9845958b35126ab3249c0b2741b52c866cebc2a62fa7d1713b2dee71c4b73d304179e7385aee978b30cc44e76956af4\" usemap=\"#fbtpf\"></td></tr></table><br />" +
												"</body>" +
												"</html>",

												"<html>" +
												"<body bgcolor=\"#FFFFFF\"><br />" +
												"<h1><center><a target=\"_blank\" href=\"http://natasterfahrten.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a6327977cf718cb2ba0e4ee655b94be6682cf5275a56417d3ac65549c43751c507b\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
												"<map name=\"dxppn\"><area shape=\"rect\" coords=\"4,2,2148,2858\" target=\"_blank\" href=\"http://natasterfahrten.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a6327977cf718cb2ba0e4ee655b94be6682cf5275a56417d3ac65549c43751c507b\"></map>" +
												"<map name=\"wikaq\"><area shape=\"rect\" coords=\"1,3,2904,2476\" target=\"_blank\" href=\"http://natasterfahrten.info/db0b993e7b373a0370b0cca089f680f4bcc63a5b865c9513a081bd77b86d37d2cc7a56f8df6f95346844a58339a43b4c7577699c00945b729a1029c7e20e4a7f\"></map>" +
												"<table height=\"320\" width=\"425\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
												"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://natasterfahrten.info/5dd7f803c0270767fad0fa43490a2176e23f3b83fbd920432d7fa137a7fdcc153a66b0576f1122004a435f8594cb585dc3747a6db9aef884369fc0a51740803a\" usemap=\"#dxppn\"></td></tr>" +
												"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://natasterfahrten.info/108de14473fbd0f11f4b3f4a6e7eba75c9845958b35126ab3249c0b2741b52c85119890937b0273d94b2f5a582303598ff0c8862ac9023313e3f3993ad072b17\" usemap=\"#wikaq\"></td></tr></table><br />" +
												"</body>" +
												"</html>"
												};
	}
	
	public interface offer_1148{
		public static final String[] froms = {
											"Window Price Offer",
											"Window Price Promo",
											"WindowPrice",
											"Window Price",
											"WindowPrice.net"
											};
		
		public static final String[] subjects = {
												"Upgrade your home with affordable window replacements",
												"Replace your windows without spending a fortune",
												"Start the year with a home window replacement",
												"New Year! New Windows!",
												"Affordable window replacement made easy",
												"Quotes on local window replacement contractors",
												"Energy Efficient Windows for Local Residents",
												"Winter Specials on Energy-Saving Windows for Local Residents",
												"Energy Efficient Windows, Winter Closeout Prices!",
												"Local Window Installers Offering Winter Specials",
												"Top Brands offering Winter closeout prices on Windows",
												"Stop Wasting Energy, Replace Windows and Save!",
												"Huge Winter Savings on Windows in your city!",
												"Best Windows Replacement Deals of the Year- Available Now.",
												"Update Your Windows and Slash Your Energy Bills!",
												"Windows Specials from Local Professionals & Other Local Contractors",
												"Window-Replacement Specials from Local Professionals, Andersen and more",
												"Window-Replacement Deals from Local Contractors, Andersen and more",
												"Local Window-replacement specials from Local Contractors and Others",
												"Savings on Windows From Local Professionals and Other Local Contractors",
												"Energy Efficient Windows From Local Professionals & Other Local Contractors",
												"Local Professionals & Local Contractors Window Replacement Deals",
												"Local Professionals & Local Contractors Energy Efficient Window Deals",
												"Quality Window Replacement Deals - Quotes from Local Professionals & Local Contractors!",
												"Window Deals from Local Professionals & other local contractors",
												"Window Replacement Estimates from Local Professionals & more",
												"Window replacement deals from Local Professionals & other local contractors",
												"Save on Windows from Local Professionals & other local contractors",
												"Free Window Replacement Estimates from Local Professionals & more",
												"Energy Efficient Windows From Local Professionals & more",
												"Energy Efficient Window Deals from Local Professionals & more",
												"Looking for window deals in your city?",
												"What are the best window deals in your city?",
												"Need new windows?",
												"Looking for great deals on windows?",
												"Check out these window deals",
												"Window deals may be available in your city",
												"Looking for hot deals on windows?",
												"Winter Window Specials in Your City",
												"Winter Window Savings on Pella, Andersen, Marvin & more",
												"Save on top window brands this season",
												"Top Window Brands Savings this Season",
												"Winter Discounts on Pella, Andersen, Marvin & more",
												"Quality Window Replacement Deals",
												"Check out these low prices on home window replacements",
												"Does your home need new windows? Check out these deals",
												"Check out the latest in replacement windows"
												};
		
			public static final String[] bodies = {
				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://collowed.info/5dc9114afa702b0aa5686278995b10690d18e9490c0167fa1e446405a712e51ce3819e7b63e730ad9f659ff440ec3750495fa61c0c9eb09a2fe9f50160687b94\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"ugbum\"><area shape=\"rect\" coords=\"1,0,2273,2483\" target=\"_blank\" href=\"http://collowed.info/5dc9114afa702b0aa5686278995b10690d18e9490c0167fa1e446405a712e51ce3819e7b63e730ad9f659ff440ec3750495fa61c0c9eb09a2fe9f50160687b94\"></map>" +
				"<map name=\"zwdnd\"><area shape=\"rect\" coords=\"3,1,2593,2034\" target=\"_blank\" href=\"http://collowed.info/5b9d3be3437b36300580bcc6e7aa504f05bc532efe5d00eb6acada0b371a332efe80d1fcc29f35838e8aa5a22bd3620b4e30b0eea62f1ca40900b6b9645db95e\"></map>" +
				"<table height=\"374\" width=\"442\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://collowed.info/5356664074e9d9594ecfdbe8ccb608464e6c241a57b7570f7af8ddd96c451ea301d158729f05020ca4d2ff15c04f5af307bdd98fbe7b395411ccb58d83e9ff7e\" usemap=\"#ugbum\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://collowed.info/5930ac7dff43229a83d463147f64511ff000d283bde6a28740512bba4fc44c639f4691bae22de179eb60300ad7df466284bd99f41abc5570639abcf3956fdedf\" usemap=\"#zwdnd\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://catholocausing.info/b95c786b70c2de0b649c99d90eec72c959a69040ef69a5b4fb37076a29b8338acaded04cfe58dec7b261c30c2f278e8f8d74fc87807528f748c799f03e89d69c\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"afryo\"><area shape=\"rect\" coords=\"1,1,2183,2280\" target=\"_blank\" href=\"http://catholocausing.info/b95c786b70c2de0b649c99d90eec72c959a69040ef69a5b4fb37076a29b8338acaded04cfe58dec7b261c30c2f278e8f8d74fc87807528f748c799f03e89d69c\"></map>" +
				"<map name=\"kwvkd\"><area shape=\"rect\" coords=\"2,2,2216,2972\" target=\"_blank\" href=\"http://catholocausing.info/0a49abc4db7be073636922c3d3d4c8ce91733575aeecec3d5831ead5b44273d8f36cd646a4e19e87dd1211162f7e3fb86f700e776d9bfa77f30a6533a60b1db3\"></map>" +
				"<table height=\"337\" width=\"458\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://catholocausing.info/149d17366d2d45ac7dcf776143f082b50a44f374b7ef3ccc56938865ebe3b75bafbacf964a5f9f2e6290eb0c28c89213dda154e160a95327deed5cc9206fcbde\" usemap=\"#afryo\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://catholocausing.info/a094187e7d6b99abda3fc6bd03f78d5d436747c82320515ec2dcf0ffaca67fd26b18ab68d14ae113823e73bcf37f080183bdb4c246eada0d329d43017eb249ec\" usemap=\"#kwvkd\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://geneveral.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da86fa1f3768d2d8ef0dee38b179cb25a0ab3ee52fd26644abf445d8ad2b21acb28\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"mektm\"><area shape=\"rect\" coords=\"3,0,2736,2012\" target=\"_blank\" href=\"http://geneveral.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da86fa1f3768d2d8ef0dee38b179cb25a0ab3ee52fd26644abf445d8ad2b21acb28\"></map>" +
				"<map name=\"gukum\"><area shape=\"rect\" coords=\"3,2,2348,2082\" target=\"_blank\" href=\"http://geneveral.info/954f20c0a4ee969aea12bb23868dc87f6f9a373076d79ed41f9af8be198f57e439767227aa288063d93dd677133eb6f3fe222d3cf811c7b7a9b2712fa8b7a56e\"></map>" +
				"<table height=\"305\" width=\"415\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://geneveral.info/51be3265a94bd6eb21ba25198e76c9d5b2dc1473d101e566cbac25644cc8ccbd3e93ef1a27f94a989d07b7d20975c4d75a219247ad1e0e232cfea1aedcb84dce\" usemap=\"#mektm\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://geneveral.info/4474869ce8643d042bd1c20dc650c30043d8dbe9b1a7e582f1f9584a627b3854fc5a08d3d9693a7b83018db8b13e9eb37ebb9af78c501c656ec34c84907a7e33\" usemap=\"#gukum\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://calculum.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb6105533eb06e2ab2d3753104c052556c60d9880a4e00eb863cb6b5871b32d8a674f54\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"gpaky\"><area shape=\"rect\" coords=\"0,3,2783,2632\" target=\"_blank\" href=\"http://calculum.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb6105533eb06e2ab2d3753104c052556c60d9880a4e00eb863cb6b5871b32d8a674f54\"></map>" +
				"<map name=\"ienbr\"><area shape=\"rect\" coords=\"3,4,2158,2200\" target=\"_blank\" href=\"http://calculum.info/51538d847a09c8205acc165697d2c01169dac8eb1cc560c6fa6424365ad92ea11b7cdc2d028c05f41592666edf41a716d78e35cee351aed4a51dc1579f442347\"></map>" +
				"<table height=\"329\" width=\"409\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://calculum.info/d049434b2d395ab8de267eb36a619c75f61482eb817906801880447391242209fbde5ac3fe4097f99ae736425a8f7221f8c45ce7e0a831d50f5bb11127e363c6\" usemap=\"#gpaky\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://calculum.info/8b127638e5fb753da75894622d649669cd0f21f6d2abf5c745b505a9c0833de013a8da47d650024659d6c98f3a11cb8dfa106d4fdd31c6dac31d59ea04cee0cd\" usemap=\"#ienbr\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://tichess.info/dc12d37d0701a79982ac111a08e1265f41ba8678faca9c61607aa13355ea4b2dcdc92e6f7749ef5e698b45548e2fd3db5df767d98264a66f3557334bdde476db\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"qlcdk\"><area shape=\"rect\" coords=\"4,3,2508,2104\" target=\"_blank\" href=\"http://tichess.info/dc12d37d0701a79982ac111a08e1265f41ba8678faca9c61607aa13355ea4b2dcdc92e6f7749ef5e698b45548e2fd3db5df767d98264a66f3557334bdde476db\"></map>" +
				"<map name=\"himjb\"><area shape=\"rect\" coords=\"2,2,2268,2107\" target=\"_blank\" href=\"http://tichess.info/70c0bfe58a8f556880021ceedbe6f6ee420e20846da5ac0453c0590ff4d68fc3ac1ebefaa44e0999f7e4c6243865de05f01cc508a667baebd9ae1fa17a71e349\"></map>" +
				"<table height=\"301\" width=\"476\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://tichess.info/f54c753083d750b41bbe1db6a2e7caf93f7ac0c929780001530b2184b814c9616345248e0e4b59ee39f99b8c2872b0b5cfcb709b49c2c4c324901c5a6080636c\" usemap=\"#qlcdk\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://tichess.info/3cfc6aa04626b72de5fdf0614e0f97c0b815e1c596cbbba0f127e6d8fe4c01a4ad6da004297cfa0b3de03e20a08b5fc737aab1413ed696c79a20e5a47dead8c3\" usemap=\"#himjb\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://localiform.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da825bf3efd9469eb6168d511b935ec06ddb4e28a803ead27e5d6bcbcfcc6acfb05\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"kpnac\"><area shape=\"rect\" coords=\"2,4,2426,2965\" target=\"_blank\" href=\"http://localiform.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da825bf3efd9469eb6168d511b935ec06ddb4e28a803ead27e5d6bcbcfcc6acfb05\"></map>" +
				"<map name=\"kgibm\"><area shape=\"rect\" coords=\"0,2,2315,2430\" target=\"_blank\" href=\"http://localiform.info/954f20c0a4ee969aea12bb23868dc87f6f9a373076d79ed41f9af8be198f57e47b84492d52d8a9b2d3f0a5f5f2277f2b84d9eac17b9f799791465833250f3653\"></map>" +
				"<table height=\"329\" width=\"430\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://localiform.info/51be3265a94bd6eb21ba25198e76c9d5b2dc1473d101e566cbac25644cc8ccbdd0b2d345c75a9f469f383c2c96e7515fd288eeec9da7861b8fd141836c772ee8\" usemap=\"#kpnac\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://localiform.info/4474869ce8643d042bd1c20dc650c30043d8dbe9b1a7e582f1f9584a627b3854ebd4937892a8fd45afcf8e573be1013c3ee2b45238b1d743fdc846020ce1e575\" usemap=\"#kgibm\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://natasterfahrten.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb610550a0e22050a7600126ad3868176daddbdd721ba5378056390e91864e9ab23c049\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"iwcyd\"><area shape=\"rect\" coords=\"2,0,2979,2718\" target=\"_blank\" href=\"http://natasterfahrten.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb610550a0e22050a7600126ad3868176daddbdd721ba5378056390e91864e9ab23c049\"></map>" +
				"<map name=\"jijhn\"><area shape=\"rect\" coords=\"2,4,2079,2617\" target=\"_blank\" href=\"http://natasterfahrten.info/51538d847a09c8205acc165697d2c01169dac8eb1cc560c6fa6424365ad92ea191e184390cf11a25959e5e05b884da33b6254ccac157c047e758d9ce05cab83e\"></map>" +
				"<table height=\"310\" width=\"458\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content" + UUID.randomUUID() + "\"" + " src=\"http://natasterfahrten.info/d049434b2d395ab8de267eb36a619c75f61482eb817906801880447391242209a6b85b937c199b8e229a44c10a6916501b453193b829a71bd46356d85395b246\" usemap=\"#iwcyd\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe" + UUID.randomUUID() + "\"" + " src=\"http://natasterfahrten.info/8b127638e5fb753da75894622d649669cd0f21f6d2abf5c745b505a9c0833de0279615b9ba59360a068be3046b455beaadfb9d869cc0c05de95215e76c2642ab\" usemap=\"#jijhn\"></td></tr></table><br />" +
				"</body>" +
				"</html>"
												};
	}
	
}
