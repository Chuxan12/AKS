<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
        <head>
            <title>Academy API</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 24px; background: #f6f8fb; }
                h1 { margin-top: 0; }
                .card { background: #fff; padding: 16px 20px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); margin-bottom: 16px; }
                ul { padding-left: 18px; }
                .link { font-size: 14px; color: #0f62fe; text-decoration: none; }
            </style>
        </head>
        <body>
        <h1>Academy API (XSL view)</h1>
        <div class="card">
            <p>Навигация:</p>
            <a class="link" href="/api/courses">/api/courses</a> |
            <a class="link" href="/api/students">/api/students</a>
        </div>
        <xsl:apply-templates/>
        </body>
        </html>
    </xsl:template>

    <xsl:template match="courses">
        <div class="card">
            <h2>Курсы</h2>
            <ul>
                <xsl:for-each select="course">
                    <li>
                        <strong><xsl:value-of select="code"/></strong>
                        — <xsl:value-of select="title"/>
                        <span> (id=<xsl:value-of select="@id"/>) </span>
                    </li>
                </xsl:for-each>
            </ul>
        </div>
    </xsl:template>

    <xsl:template match="students">
        <div class="card">
            <h2>Студенты</h2>
            <ul>
                <xsl:for-each select="student">
                    <li>
                        <strong><xsl:value-of select="fullName"/></strong>
                        (<xsl:value-of select="email"/>), курс:
                        <xsl:value-of select="course/code"/>
                    </li>
                </xsl:for-each>
            </ul>
        </div>
    </xsl:template>
    <xsl:template match="text()"/>
</xsl:stylesheet>
