package hk.ust.cse.voicerecognizer;

import hk.ust.cse.utils.AppLog;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.voicerecognizer.R;

public class MainActivity extends Activity implements ResultListener {
	private Recorder recorder = new Recorder();
	private RemoteRecognizer recognizer = new RemoteRecognizer(this);
	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setButtonHandlers();
		enableButtons(false);
		text = (TextView) findViewById(R.id.MessageText);
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
				stopRecording();
				recognizer.requestServer(recorder.getSaveFilePath());
				break;
			}
			}
		}
	};

	@Override
	public void receiveResult(String result) {
		text.setText(result);
	}
}
