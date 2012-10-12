package net.jingx.otpAuth;

import java.util.LinkedHashMap;
import java.util.List;

import net.jingx.otpAuth.model.IProvider;
import net.jingx.otpAuth.otp.PinGenerator;
import net.jingx.otpAuth.otp.TimerNextKey;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainSwt {

	protected Shell shell;
	private Label lblTimerDo;
	private List<IProvider> listProvider;
	private Composite innerScrollComposite;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainSwt window = new MainSwt();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(3, false));
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				SWTResourceManager.dispose();
			}
		});

		Label lblTimer = new Label(shell, SWT.NONE);
		lblTimer.setText("Timer:");

		lblTimerDo = new Label(shell, SWT.NONE);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		btnNewButton.setText("+");

		ScrolledComposite scrolledComposite = new ScrolledComposite(shell,
				SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 3, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		innerScrollComposite = new Composite(scrolledComposite, SWT.NONE);
		innerScrollComposite.setLayout(new GridLayout(1, false));

		scrolledComposite.setContent(innerScrollComposite);
		scrolledComposite.setMinSize(innerScrollComposite.computeSize(
				SWT.DEFAULT, SWT.DEFAULT));

		initProvider();
		generateAndDisplayPin();
		initTimer();
	}

	private void initProvider() {
		listProvider = SecretFileManager.read();
	}

	private void initTimer() {
		shell.getDisplay().timerExec(TimerNextKey.TIME,
				new TimerNextKey(shell, lblTimerDo, new Listener() {
					@Override
					public void handleEvent(Event event) {
						generateAndDisplayPin();
					}
				}));

	}

	private LinkedHashMap<String, Composite> mapProviderControl = new LinkedHashMap<String, Composite>();

	private void generateAndDisplayPin() {
		for (IProvider provider : listProvider) {
			try {
				Composite comp = mapProviderControl.get(provider.getSecret());
				if (comp == null) {
					comp = makeProviderComposite();
					mapProviderControl.put(provider.getSecret(), comp);
				}
				String pin = PinGenerator
						.computePin(provider.getSecret(), null);
				((Label) comp.getChildren()[0]).setText(pin);
				((Label) comp.getChildren()[1]).setText(provider.getName());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Composite makeProviderComposite() {
		Composite composite = new Composite(innerScrollComposite, SWT.BORDER);
		composite.setLayout(new GridLayout(1, false));
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1);
		composite.setLayoutData(gd_composite);

		Label lblPin = new Label(composite, SWT.NONE);
		lblPin.setFont(SWTResourceManager.getFont("Lucida Grande", 30,
				SWT.NORMAL));
		lblPin.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false,
				1, 1));
		lblPin.setText("New Label");

		Label lblName = new Label(composite, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Lucida Grande", 15,
				SWT.NORMAL));
		lblName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false,
				1, 1));
		lblName.setBounds(0, 0, 59, 14);
		lblName.setText("New Label");
		return composite;
	}
}
