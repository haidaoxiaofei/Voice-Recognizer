package hk.ust.cse.voicerecognizer;

import hk.ust.cse.utils.AppLog;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.voicerecognizer.R;

public class MainActivity extends Activity implements ResultListener {
	private Recorder recorder = new Recorder();
	private RemoteRecognizer recognizer = new RemoteRecognizer(this);
	private TextView text;
	private long startTime = 0;
	private long endTime = 0;
	private long recognizeTime = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setButtonHandlers();
		enableButtons(false);
		text = (TextView) findViewById(R.id.MessageText);
		recorder.setUID(getMacAddress());
	}

	public String getMacAddress() {
		WifiManager wimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		String macAddress = wimanager.getConnectionInfo().getMacAddress();
		if (macAddress == null) {
			macAddress = "Device don't have mac address or wi-fi is disabled";
		}
		return macAddress;
	}

	private void setButtonHandlers() {
		((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
	}

	private void enableButton(int id, boolean isEnable) {
		((Button) findViewById(id)).setEnabled(isEnable);
	}

	private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnStart, !isRecording);
		enableButton(R.id.btnStop, isRecording);
	}

	private void startRecording() {
		recorder.startRecording();
	}

	private void stopRecording() {
		recorder.stopRecording();
	}

	private View.OnClickListener btnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStart: {
				AppLog.logString("Start Recording");

				enableButtons(true);
				startRecording();

				break;
			}
			case R.id.btnStop: {
				AppLog.logString("Start Recording");

				enableButtons(false);
				startTime = System.currentTimeMillis();
				stopRecording();
				endTime = System.currentTimeMillis();
				recognizer.requestServer(recorder.getSaveFilePath());
				break;
			}
			}
		}
	};

	@Override
	public void receiveResult(String result) {
		recognizeTime = System.currentTimeMillis();
		text.setText(result + ";" + (endTime - startTime) + ";"
				+ (recognizeTime - endTime));
	}
}
