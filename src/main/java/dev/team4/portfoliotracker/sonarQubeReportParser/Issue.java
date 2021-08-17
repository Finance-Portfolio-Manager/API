package dev.team4.portfoliotracker.sonarQubeReportParser;

import org.json.JSONException;
import org.json.JSONObject;

public class Issue
{
    public final String issueKey;
    public final String filePath;
    public final String fileName;
    public final String severity;
    public final String issueLine;
    public final String sonarRecommendation;

    public Issue(JSONObject jsonIssue) throws JSONException
    {
        this.issueKey = jsonIssue.getString("key");
        this.filePath = jsonIssue.getString("component");
        this.fileName = getFilename(this.filePath);
        String severityApplicable = "N/A";
        try
        {
            severityApplicable = jsonIssue.getString("severity");
        }
        catch (JSONException e)
        {

        }
        finally
        {
            this.severity = severityApplicable;
        }

        String lineApplicable = "N/A";
        try
        {
            lineApplicable = jsonIssue.getString("line");
        }
        catch (JSONException e)
        {

        }
        finally
        {
            this.issueLine = lineApplicable;
        }

        this.sonarRecommendation = jsonIssue.getString("message");
    }
        /*
        public Issue(String issueKey, String filePath)
        {
            this.issueKey = issueKey;
            this.filePath = filePath;
            this.fileName = getFilename(filePath);
        }
         */

    private String getFilename(String filePath)
    {
        int lastSlashIndex = filePath.lastIndexOf('/');
        return filePath.substring(lastSlashIndex);
    }

    public String toString()
    {
        /*
        System.out.println("OBJECT DETAILS:");
        System.out.println(issueKey);
        System.out.println(fileName);
        System.out.println(filePath);
        System.out.println(severity);
        System.out.println(sonarRecommendation);
         */
        String asString = "=================================================\n";
        asString += "Issue ID: " + issueKey;
        asString += "\n";
        asString += "Severity: " + severity;
        asString += "\n";
        asString += "File: " + fileName;
        asString += "\n";
        asString += "Problem starts on line: " + issueLine;
        asString += "\n";
        asString += "Recommended action: " + sonarRecommendation;
        asString += "\n";
        asString += "Full file path: " + filePath;
        asString += "\n";
        asString += "=================================================";

        return asString;
    }
}
