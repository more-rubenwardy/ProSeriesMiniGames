function GetSiteSearchResults(newWindow,frameObject,frameObjectName,fontFace,fontSize,fontColour,linkFace,linkSize,linkColour,resultsText)
{
var sTerms="";
var iDepth = 0;
var sURL = new String(document.location);
if (sURL.indexOf("?") > 0)
{
var arrParams = sURL.split("?");
var arrURLParams = arrParams[1].split("&");
for (var i=0;i<arrURLParams.length;i++)
{
var sParam = arrURLParams[i].split("=");
var sValue = unescape(sParam[1]);
if( sParam[0] == frameObjectName)
	sTerms = sValue;
if( sParam[0] == "depth")
	iDepth = parseInt(sValue);
}
}
var d=frameObject.document;
if (sTerms=="") {d.open(); d.write("<html><head></head><body style=\"background: transparent;\"></body></html>"); d.close();return;}
var sBack=""; for (i=0; i<iDepth; i++) sBack+='..\\\\';
d.open();
d.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
d.write("<html lang=\"en\">");
d.write("<head>");
d.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
d.write("</head>");
d.write("<body style=\"margin: 0px 0px 0px 0px; font-family: "+fontFace+"; font-size: "+fontSize+"; color: "+fontColour+"; background: transparent;\">");
d.write("<div id=\"wpSearchResults\"></div>");
d.write("<script type=\"text/javascript\">");
d.write("var wordMap = new Array(\" welcome series developed multa tabs navigate javascript users there links bottom this manual series \",\" using main menu when game loads logo shown then main menu appears start game move ball that represents click 'go‘ information navigate arrows click close \",\" \",\" flying simple flying game around massive landscape controls button down down left left right right thrust brake \",\" racing multi player racing mini game this version series there only track next will have tracks controls [p1] accelerate brake left right [p2] down turn \",\" shooting shoot many targets possible minutes controls arrows move space \",\" obstacle gargoyle hungry navigate through maze past obstacles kitchen this version series there only course next will have multi tracks controls arrows move space jump shoot tilt camera \",\" help fight kill other player flying combat player down left right thrust brake shoot arrow keys move delete page down \",\" navigate through massive maze arrows move help amazing \",\" jumping press space jump knocked spinning there levels last level logs right-angles each other when fall game over screen appears asks want play again \",\" hide seek simple hide seek game arrow keys move press space show allowed difficultly player hiders seeker hider controls enter confirm hiding place finder when find player click with cursor \");");
d.write("var pageMap = new Array(\"Home\",\"Main Menu\",\"Search Results\",\"Flying Pro\",\"Racing Pro\",\"Shooting Pro\",\"Obstacle Pro\",\"Dog Fighting Pro\",\"Amazing Pro\",\"Jumping Pro\",\"Hide and Seek Pro\");");
d.write("var linkMap = new Array(\"index.html\",\"mainmenu.html\",\"search.html\",\"flying.html\",\"racing.html\",\"shooting.html\",\"obstacle.html\",\"dogfight.html\",\"amazing.html\",\"jumping.html\",\"hidenseek.html\");");
d.write("var preMap = new Array(\"Welcome To Pro Series Pro Series is developed by Multa. Use the tabs to navigate For non JavaScript users there are links at the  bottom  This is the manual for the series \",\"Using The Main Menu When the Game loads the logo is shown and  then the main menu appears.  To start a game, Move to the ball that represents it and click ’go‘  To get information on that game, click \",\"\",\"Flying pro is a simple flying game. Fly around a Massive landscape.   CONTROLS: UP BUTTON: Down DOWN BUTTON: UP LEFT BUTTON: Left RIGHT BUTTON: Right A: Thrust Z: Brake \",\"Racing PRO is a multi player racing mini Game  In this version of PRO SERIES  there is only one track The next version  will have multi tracks  CONTROLS: [P1]  W Accelerate Z  Brake A  Left S  Right  \",\"Shooting PRO:  Shoot as many targets as possible in  3 Minutes  CONTROLS:  ARROWS: Move SPACE: Shoot  \",\"OBSTACLE PRO  The gargoyle is hungry Navigate through the maze  and get past the obstacles  to the kitchen  In this version of PRO SERIES  there is only one course The Next version will have multi tra\",\"HELP ON DOG FIGHT PRO  Kill the other player! A Flying Combat  PLAYER 1  W/Z Down/UP A/S Left/Right E Thrust D Brake 1 Shoot  PLAYER 2  ARROW KEYS Move DELETE Thrust END Brake PAGE DOWN Shoot \",\"Navigate through the massive maze  ARROWS: Move  Help on amazing pro \",\"Jumping Pro Press space to jump. The aim is to not be knocked off by the spinning log. There are 6 levels  The last level is 4 logs spinning  at right-angles to each other   When you fall off, The gam\",\"Hide and Seek pro is a simple hide  and seek game. Use the Arrow Keys to move. Press space to show map  (if allowed on game difficultly)   3 Player game - 2 Hiders, 1 Seeker  HIDER CONTROLS Press ente\");");
d.write("function doNav(ind)");
d.write("{");
if (newWindow)
d.write("		 window.open(\""+sBack+"\"+linkMap[ind],\"_blank\");");
else
d.write("		 parent.window.location.href=linkMap[ind];");
d.write("}");
d.write("function wpDoSearch(searchTerms){");
d.write("var terms = searchTerms.split(\" \");");
d.write("if (terms==\"\") return;");
d.write("var results = \"\";");
d.write("var resultscount = 0;");
d.write("for (var i=0; i<wordMap.length; i++)");
d.write("{");
d.write("			var found=true;");
d.write("			for (var j=0; j<terms.length; j++)");
d.write("					if (wordMap[i].indexOf(terms[j].toLowerCase())==-1) found=false;");
d.write("			if (found)");
d.write("			{");
d.write("				 results+=\"<a style=\\\"cursor: pointer; font-family: "+linkFace+"; font-size: "+linkSize+"; color: "+linkColour+"; \\\" onclick=\\\"doNav(\"+i+\");\\\"><u>\"+pageMap[i]+\"</u></a><br>\"+preMap[i]+\"...<br><br>\";");
d.write("				 resultscount++;");
d.write("			}");
d.write("}");
d.write("document.getElementById(\"wpSearchResults\").innerHTML=resultscount+\" "+resultsText+" \"+searchTerms+\"<br><br>\"+results;");
d.write("}");
while(sTerms.indexOf("\"") != -1 ) {
sTerms = sTerms.replace("\"","");
};
d.write("wpDoSearch(\""+sTerms+"\");");
d.write("</script>");
d.write("</body></html>");
d.close();
}