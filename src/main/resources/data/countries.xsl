<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="/">
    <html>
      <head>
        <script src="js/jquery-3.4.1.min.js"/>
        <script src="js/bootstrap.min.js"/>
        <link href="css/bootstrap.min.css" rel="stylesheet"/>
      </head>
      <body>
        <xsl:for-each select="//countries/element">
          <p>
            <xsl:value-of select="name"/>
            <i class="fas fa-flag"/>
          </p>
        </xsl:for-each>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
