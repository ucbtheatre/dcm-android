package com.ucb.dcm.net;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ExecuteURLDownload extends AsyncTask<HttpURLConnection, Integer, JSONObject>
{
    public interface ExecuteURLDownloadListener
    {
        public void onSuccess(JSONObject result);
        public void onError(JSONObject result);
    }

    public static String ERROR_KEY = "url-error-key";

    private ExecuteURLDownloadListener listener;

    public ExecuteURLDownload(ExecuteURLDownloadListener listener)
    {
        this.listener = listener;
    }

    protected JSONObject doInBackground(HttpURLConnection... conns)
    {
        JSONObject retVal = null;

        for(int i = 0; i < conns.length; i++)
        {
            HttpURLConnection c = conns[i];
            Log.v("Executed URL", c.toString());
            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                retVal = new JSONObject(sb.toString());
            }
            catch(Exception e)
            {
                Log.e("URL error", e.getLocalizedMessage());

                retVal = new JSONObject();
                try
                {
                    retVal.put(ERROR_KEY, e.getLocalizedMessage());
                }
                catch(JSONException je)
                {
                    je.printStackTrace();
                }
            }
        }
        return retVal;
    }

    protected void onPostExecute(JSONObject result)
    {
        if(result.has(ERROR_KEY))
        {
            this.listener.onError(result);
        }
        else
        {
            this.listener.onSuccess(result);
        }
    }
}