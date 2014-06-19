package hk.ust.cse.voicerecognizer;

import hk.ust.cse.utils.UploadUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import android.os.AsyncTask;

public class RemoteRecognizer {
	private static String server = "http://gmission-asia.cloudapp.net/voice/index.php/welcome/recognize";
	public ResultListener listener;
	
	
	public RemoteRecognizer(ResultListener listener) {
		super();
		this.listener = listener;
	}



	public void requestServer(String voiceFilePath){
		new AsyncHttpPostTask(server).execute(new File(voiceFilePath));
	}
	
	
	
	public class AsyncHttpPostTask extends AsyncTask<File, Void, String> {
	    private String server;

	    public AsyncHttpPostTask(final String server) {
	        this.server = server;
	    }

	    @Override
	    protected String doInBackground(File... params) {
	        String result = UploadUtil.uploadFile(params[0], this.server);
	        return result;
	    }

		@Override
		protected void onPostExecute(String result) {

			listener.receiveResult(result);

			super.onPostExecute(result);
		}
	    
	    
	}
}
