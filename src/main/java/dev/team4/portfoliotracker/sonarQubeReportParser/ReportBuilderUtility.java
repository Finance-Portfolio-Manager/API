package dev.team4.portfoliotracker.sonarQubeReportParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ReportBuilderUtility
{
    public static void main(String[] args)
    {
        //If you're looking at this code, you probably shouldn't be.
        //If you're running this code, you probably shouldn't be.

        try
        {
            //URL to get all issues from current projects from local SonarQube server; ps=page size, p=page
            URL url = new URL("http://localhost:9000/api/issues/search?ps=500&statuses=OPEN,REOPENED");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status;

            //Execute, and output response code
            status = connection.getResponseCode();
            //System.out.println("HTTP-Status: " + status);

            //Read in the whole response
            BufferedReader respReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer jsonResponseAsString = new StringBuffer();
            while((line = respReader.readLine())!=null)
            {
                jsonResponseAsString.append(line);
            }
            respReader.close();
            connection.disconnect();
            //System.out.println("JSON content is:");
            //System.out.println(jsonResponseAsString);

            JSONObject reportJson = new JSONObject(jsonResponseAsString.toString());
            //System.out.println("ReportJson to string is:");
            //System.out.println(reportJson.toString());

            JSONArray issuesArray = reportJson.getJSONArray("issues");
            //System.out.println("Issues array is:");
            //System.out.println(testArray.toString());

            //System.out.println("Issues array index item 0 is:");
            //System.out.println(issuesArray.getJSONObject(0).toString());

            outputAllIssues(issuesArray);

        } catch (MalformedURLException e)
        {
            //e.printStackTrace();
            System.out.println("URL malformed.");
        } catch (IOException e)
        {
            System.out.println("Issue opening connection.");
            //e.printStackTrace();
        } catch (JSONException e)
        {
            System.out.println("Error creating JSONObject.");
            //e.printStackTrace();
        }
    }

    private static String generateStringReport(Issue[] issues)
    {
        StringBuilder output = new StringBuilder("");

        //Organize data
        //ArrayList<String> severities = new ArrayList<>();
        //severities.add("N/A");
        //severities.add("INFO");
        //severities.add("MINOR");
        //severities.add("MAJOR");
        //severities.add("CRITICAL");
        //severities.add("BLOCKER");

        //Sort the data
        ArrayList<Issue> naIssues = new ArrayList<>();
        ArrayList<Issue> infoIssues = new ArrayList<>();
        ArrayList<Issue> minorIssues = new ArrayList<>();
        ArrayList<Issue> majorIssues = new ArrayList<>();
        ArrayList<Issue> criticalIssues = new ArrayList<>();
        ArrayList<Issue> blockerIssues = new ArrayList<>();

        for (int x=0; x<issues.length;x++)
        {
            //Check each issue, adding it to the appropriate list
            Issue currentIssue = issues[x];

            switch (currentIssue.severity)
            {
                case "N/A":
                    naIssues.add(currentIssue);
                    break;
                case "INFO":
                    infoIssues.add(currentIssue);
                    break;
                case "MINOR":
                    minorIssues.add(currentIssue);
                    break;
                case "MAJOR":
                    majorIssues.add(currentIssue);
                    break;
                case "CRITICAL":
                    criticalIssues.add(currentIssue);
                    break;
                case "BLOCKER":
                    blockerIssues.add(currentIssue);
                    break;
            }
        }


        output.append("------ISSUE REPORT------\n");
        output.append("------------------------");
        output.append("\n");
        output.append("Severity levels, from least to greatest:\n");
        output.append("N/A, INFO, MINOR, MAJOR, CRITICAL, BLOCKER\n");
        output.append("------------------------\n");
        output.append("Total number of BLOCKER issues: " + blockerIssues.size() + "\n");
        output.append("Total number of CRITICAL issues: " + criticalIssues.size() + "\n");
        output.append("Total number of MAJOR issues: " + majorIssues.size() + "\n");
        output.append("Total number of MINOR issues: " + minorIssues.size() + "\n");
        output.append("Total number of INFO issues: " + infoIssues.size() + "\n");
        output.append("Total number of N/A-tier issues: " + naIssues.size() + "\n");
        output.append("------------------------\n");
        output.append("------------------------\n");
        if (blockerIssues.size()==0)
            output.append("No BLOCKER issues detected.\n");
        else
        {
            output.append("BLOCKER ISSUES:\n");
            for (Issue issue:blockerIssues)
            {
                output.append(issue.toString());
                output.append("\n");
            }
        }

        if (criticalIssues.size()==0)
            output.append("No CRITICAL issues detected.\n");
        else
        {
            output.append("CRITICAL ISSUES:\n");
            for (Issue issue:criticalIssues)
            {
                output.append(issue.toString());
                output.append("\n");
            }
        }

        if (majorIssues.size()==0)
            output.append("No MAJOR issues detected.\n");
        else
        {
            output.append("MAJOR ISSUES:\n");
            for (Issue issue:majorIssues)
            {
                output.append(issue.toString());
                output.append("\n");
            }
        }

        if (minorIssues.size()==0)
            output.append("No MINOR issues detected.\n");
        else
        {
            output.append("MINOR ISSUES:\n");
            for (Issue issue:minorIssues)
            {
                output.append(issue.toString());
                output.append("\n");
            }
        }

        if (infoIssues.size()==0)
            output.append("No INFO issues detected.\n");
        else
        {
            output.append("INFO ISSUES:\n");
            for (Issue issue:infoIssues)
            {
                output.append(issue.toString());
                output.append("\n");
            }
        }

        if (naIssues.size()==0)
            output.append("No N/A-tier issues detected.\n");
        else
        {
            output.append("N/A-tier ISSUES:\n");
            for (Issue issue:naIssues)
            {
                output.append(issue.toString());
                output.append("\n");
            }
        }

        output.append("-----END OF REPORT-----END OF REPORT-----END OF REPORT-----");

        return output.toString();
    }

    public static String outputAllIssues(JSONArray issues)
    {
        String issuesString = "";
        issuesString+= "Currently reporting " + issues.length() + "issues";
        issuesString+="\n";

        Issue[] problems = new Issue[issues.length()];

        ////////////////////////////////////////////////////////////////////////////////////
        for(int x=0;x<issues.length();x++)
        {
            try
            {
                problems[x] = new Issue(issues.getJSONObject(x));
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////

        //Test one issue output:
        //System.out.println("PROBLEM ZERO IS:");
        //System.out.println(problems[0].toString());

        issuesString = generateStringReport(problems);

        System.out.println(issuesString);

        return issuesString;
    }
}
