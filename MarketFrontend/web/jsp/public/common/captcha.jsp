<%@ page import="java.util.*"%>
<%@ page import="java.lang.*" %>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.awt.*"%>
<%@ page import="java.awt.image.*"%>
<%@ page import="javax.imageio.*"%>
<%@ page import="java.awt.geom.*"%>
<%
 //========================================================
 // Kick Ass Captcha JSP
 //
 // Michael Connor 2007
 //
 // I just couldn't handle the thought of downloading a
 // big jar and configuring some servlet.xml and having
 // little to no control of anything...
 // You can send in height and width parameters.
 // The captcha value will be placed in the session in
 // a parameter called 'captcha'
 //
 // Feel free to use this code and do whatever the hell
 // you want to it.
 //========================================================

  String imageFormat = "jpg";
  response.setContentType("image/" + imageFormat);

 try {
   // you can pass in fontSize, width, height via the request
   
   ResourceBundle resBundle = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
   int backColorRed = Integer.parseInt(resBundle.getString("captcha.background.color.red"));
   int backColorGreen = Integer.parseInt(resBundle.getString("captcha.background.color.green"));
   int backColorBlue = Integer.parseInt(resBundle.getString("captcha.background.color.blue"));
   int borderColorRed = Integer.parseInt(resBundle.getString("captcha.border.color.red"));
   int borderColorGreen = Integer.parseInt(resBundle.getString("captcha.border.color.green"));
   int borderColorBlue = Integer.parseInt(resBundle.getString("captcha.border.color.blue"));
   int textColorRed = Integer.parseInt(resBundle.getString("captcha.text.color.red"));
   int textColorGreen = Integer.parseInt(resBundle.getString("captcha.text.color.green"));
   int textColorBlue = Integer.parseInt(resBundle.getString("captcha.text.color.blue"));
   int circleColorRed = Integer.parseInt(resBundle.getString("captcha.circle.color.red"));
   int circleColorGreen = Integer.parseInt(resBundle.getString("captcha.circle.color.green"));
   int circleColorBlue = Integer.parseInt(resBundle.getString("captcha.circle.color.blue"));
   String fontName = resBundle.getString("captcha.text.font.name");
   int fontSize = Integer.parseInt(resBundle.getString("captcha.text.font.size"));
   int charsToPrint = Integer.parseInt(resBundle.getString("captcha.characters.number"));
   int circlesToDraw = Integer.parseInt(resBundle.getString("captcha.circles.number"));
   int width = Integer.parseInt(resBundle.getString("captcha.width"));
   int height = Integer.parseInt(resBundle.getString("captcha.height"));
   
   Color backgroundColor = new Color(backColorRed, backColorGreen, backColorBlue);
   Color borderColor = new Color(borderColorRed, borderColorGreen, borderColorBlue);
   Color textColor = new Color(textColorRed, textColorGreen, textColorBlue);
   Color circleColor = new Color(circleColorRed, circleColorGreen, circleColorBlue);
   Font textFont = new Font(fontName, Font.PLAIN, fontSize);
   
   float horizMargin = 20.0f;
   float imageQuality = 0.95f; // max is 1.0 (this is for jpeg)
   double rotationRange = 0.7; // this is radians
   BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

   Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

   g.setColor(backgroundColor);
   g.fillRect(0, 0, width, height);

   // lets make some noisey circles
   g.setColor(circleColor);
   for ( int i = 0; i < circlesToDraw; i++ ) {
     int circleRadius = (int) (Math.random() * height / 2.0);
     int circleX = (int) (Math.random() * width - circleRadius);
     int circleY = (int) (Math.random() * height - circleRadius);
     g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
   }

   g.setColor(textColor);
   g.setFont(textFont);

   FontMetrics fontMetrics = g.getFontMetrics();
   int maxAdvance = fontMetrics.getMaxAdvance();
   int fontHeight = fontMetrics.getHeight();

   // i removed 1 and l and i because there are confusing to users...
   // Z, z, and N also get confusing when rotated
   // 0, O, and o are also confusing...
   // lowercase G looks a lot like a 9 so i killed it
   // this should ideally be done for every language...
   // i like controlling the characters though because it helps prevent confusion
   String elegibleChars = "abcdefhjkmnpqrstuvwxy23456789";
   char[] chars = elegibleChars.toCharArray();

   float spaceForLetters = -horizMargin * 2 + width;
   float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);

   AffineTransform transform = g.getTransform();

   StringBuffer finalString = new StringBuffer();

   for ( int i = 0; i < charsToPrint; i++ ) {
     double randomValue = Math.random();
     int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
     char characterToShow = chars[randomIndex];
     finalString.append(characterToShow);

     // this is a separate canvas used for the character so that
     // we can rotate it independently
     int charImageWidth = maxAdvance * 2;
     int charImageHeight = fontHeight * 2;
     int charWidth = fontMetrics.charWidth(characterToShow);
     int charDim = Math.max(maxAdvance, fontHeight);
     int halfCharDim = (int) (charDim / 2);

     BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
     Graphics2D charGraphics = charImage.createGraphics();
     charGraphics.translate(halfCharDim, halfCharDim);
     double angle = (Math.random() - 0.5) * rotationRange;
     charGraphics.transform(AffineTransform.getRotateInstance(angle));
     charGraphics.translate(-halfCharDim,-halfCharDim);
     charGraphics.setColor(textColor);
     charGraphics.setFont(textFont);

     int charX = (int) (0.5 * charDim - 0.5 * charWidth);
     charGraphics.drawString("" + characterToShow, charX, 
                            (int) ((charDim - fontMetrics.getAscent()) 
                                   / 2 + fontMetrics.getAscent()));

     float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
     int y = (int) ((height - charDim) / 2);
//System.out.println("x=" + x + " height=" + height + " charDim=" + charDim + " y=" + y + " advance=" + maxAdvance + " fontHeight=" + fontHeight + " ascent=" + fontMetrics.getAscent());
     g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);

     charGraphics.dispose();
   }

   // let's do the border
   g.setColor(borderColor);
   g.drawRect(0, 0, width - 1, height - 1);

   //Write the image as a jpg
   Iterator iter = ImageIO.getImageWritersByFormatName(imageFormat);
   if( iter.hasNext() ) {
     ImageWriter writer = (ImageWriter)iter.next();
     ImageWriteParam iwp = writer.getDefaultWriteParam();
     if ( imageFormat.equalsIgnoreCase("jpg") || imageFormat.equalsIgnoreCase("jpeg") ) {
       iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
       iwp.setCompressionQuality(imageQuality);
     }
     writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
     IIOImage imageIO = new IIOImage(bufferedImage, null, null);
     writer.write(null, imageIO, iwp);
   } else {
     throw new RuntimeException("no encoder found for jsp");
   }

   // let's stick the final string in the session
   //SL-19820
   String id = null;
   String soId = request.getParameter("soId");
   String groupId = request.getParameter("groupId");
   if(!groupId.equalsIgnoreCase("")){
   		id = groupId;
   }else if(!soId.equalsIgnoreCase("")){
   		id = soId;
   }
   //request.getSession().setAttribute("captcha", finalString.toString());
   request.getSession().setAttribute("captcha_"+id, finalString.toString());
   
   g.dispose();
 } catch (IOException ioe) {
   throw new RuntimeException("Unable to build image" , ioe);
 }
 
 out.clear();
 out = pageContext.pushBody(); 
%>

<%!
  public static String paramString(HttpServletRequest request, String paramName, String defaultString) {
    return request.getParameter(paramName) != null ? request.getParameter(paramName) : defaultString;
  }

  public static int paramInt(HttpServletRequest request, String paramName, int defaultInt) {
    return request.getParameter(paramName) != null ? Integer.parseInt(request.getParameter(paramName)) : defaultInt;
  }

%>
