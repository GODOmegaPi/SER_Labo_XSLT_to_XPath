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
        <xsl:for-each select="//countries/element[region = 'Europe' and (languages/element/name = 'English') and area &gt; 10 and area &lt; 42900]">
          <p data-toggle="modal">
            <xsl:attribute name="data-target">
              <xsl:value-of select="concat('#', alpha3Code)"/>
            </xsl:attribute>
            <xsl:value-of select="translations/fr"/>
            <img height="20" width="25">
              <xsl:attribute name="src">
                <xsl:value-of select="flag"/>
              </xsl:attribute>
            </img>
          </p>
          <div class="modal fade" role="dialog" tab-index="-1">
            <xsl:attribute name="id">
              <xsl:value-of select="alpha3Code"/>
            </xsl:attribute>
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title">
                    <xsl:value-of select="translations/fr"/>
                  </h5>
                </div>
                <div class="modal-body">
                  <div class="container-fluid">
                    <div class="row">
                      <div class="col-md-6">
                        <img height="125" width="150">
                          <xsl:attribute name="src">
                            <xsl:value-of select="flag"/>
                          </xsl:attribute>
                        </img>
                      </div>
                      <div class="col-md-6">
                        <p>
                          <xsl:value-of select="concat('Capitale: ', capital)"/>
                        </p>
                        <p>
                          <xsl:value-of select="concat(concat('Population: ', population), ' habitants')"/>
                        </p>
                        <p>
                          <xsl:value-of select="concat(concat('Superficie: ', area), ' km^2')"/>
                        </p>
                        <p>
                          <xsl:value-of select="concat('Continent: ', region)"/>
                        </p>
                        <p>
                          <xsl:value-of select="concat('Sous-Continent: ', subregion)"/>
                        </p>
                      </div>
                    </div>
                  </div>
                  <h2>Langues parlées</h2>
                  <xsl:for-each select="languages/element">
                    <p>
                      <xsl:value-of select="name"/>
                    </p>
                  </xsl:for-each>
                </div>
                <div class="modal-footer">
                  <button class="btn btn-primary" data-dismiss="modal" type="button">Fermer</button>
                </div>
              </div>
            </div>
          </div>
        </xsl:for-each>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
