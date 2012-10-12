package net.jingx.otpAuth.otp;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class TimerNextKey implements Runnable {

	public static final int TIME = 1000;
	private Shell shell;
	private Label lblTimerDo;
	private Listener execute;
	private int counter;

	public TimerNextKey(Shell shell, Label lblTimerDo, Listener execute) {
		this.shell = shell;
		this.lblTimerDo = lblTimerDo;
		this.execute = execute;
		this.counter = PasscodeGenerator.INTERVAL;
		lblTimerDo.setText(counter + " sec");
	}

	@Override
	public void run() {
		counter--;
		lblTimerDo.setText(counter + " sec");
		lblTimerDo.pack();
		if (counter == 0) {
			counter = PasscodeGenerator.INTERVAL;
			execute.handleEvent(null);
		}
		shell.getDisplay().timerExec(TIME, this);
	}

}
