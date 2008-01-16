package nz.ac.massey.take.takeep.actionsSets;

import java.util.LinkedList;
import java.util.StringTokenizer;

import nz.ac.massey.take.takeep.editor.TakeEditor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.internal.editors.text.FileEditorInputAdapterFactory;

public class TakeCompileWizardPanel extends WizardPage
{

	protected TakeCompileWizardPanel(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private String packageName = "";
	private String className = "KB";

	private String sourceOutputLocation = "";

	private LinkedList<Object> importStatements = new LinkedList<Object>();

	private LinkedList<Object> additionalInterfaces = new LinkedList<Object>();

	private boolean autoAnotate  = true;
	private boolean sourceTransform = true;


	@Override
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite composite= new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		int nColumns= 4;

		GridLayout layout= new GridLayout();
		layout.numColumns= nColumns;		
		composite.setLayout(layout);

		addNamingPanel(composite,nColumns);
		addLocationPanel(composite,nColumns);


		addImportPanel(composite,nColumns);

		addInterfacePanel(composite, nColumns);

		addCheckPanel(composite, nColumns);
		this.setDescription("Compile Wizard");
		this.setTitle("Compile Wizard");

		this.setControl(composite);
	}


	private void addNamingPanel(Composite parent, int numcols)
	{
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(2, false));
		topLevel.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,numcols,1));

		Label pnLbl = new Label(topLevel,SWT.CENTER);
		pnLbl.setText("Package Name");

		final Text pnTB = new Text(topLevel,SWT.SINGLE);
		pnTB.setText(packageName);
		pnTB.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
		pnTB.addListener(SWT.Modify, new Listener(){

			@Override
			public void handleEvent(Event event) {
				packageName = pnTB.getText();

			}});

		Label cnLbl = new Label(topLevel,SWT.CENTER);
		cnLbl.setText("Class Name");

		final Text cnTB = new Text(topLevel,SWT.SINGLE);
		cnTB.setText(className);
		cnTB.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
		cnTB.addListener(SWT.Modify, new Listener(){

			@Override
			public void handleEvent(Event event) {
				className = cnTB.getText();

			}});
	}

	private void addLocationPanel(Composite parent, int numcols)
	{
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(2, false));
		topLevel.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,numcols,1));

		Label lLbl = new Label(topLevel,SWT.CENTER);
		lLbl.setText("Source Output Location Folder");

		final Text lTB = new Text(topLevel,SWT.SINGLE);
		lTB.setText(sourceOutputLocation);
		lTB.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));

		lTB.addListener(SWT.Modify, new Listener(){

			@Override
			public void handleEvent(Event event) {
				sourceOutputLocation = lTB.getText();

			}});


	}

	private void addImportPanel(Composite parent, int numcols)
	{
//		Composite topLevel = new Composite(parent, SWT.NONE);
//		topLevel.setLayout(new GridLayout(1,false));
//		topLevel.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,numcols,1));
//
//		Label iLbl = new Label(topLevel,SWT.CENTER);
//		iLbl.setText("Additional Import Statements");
//
//		final Text iTB = new Text(topLevel,SWT.MULTI | SWT.V_SCROLL);
//		iTB.setText(sourceOutputLocation);
//		iTB.setLayoutData(new GridData(GridData.FILL, GridData.FILL,true,true));
//		iTB.addListener(SWT.Modify, new Listener(){
//			@Override
//			public void handleEvent(Event event) {
//				importStatements.clear();
//				StringTokenizer st = new StringTokenizer(iTB.getText(),iTB.getLineDelimiter());
//
//				while(st.hasMoreTokens())
//				{
//					importStatements.add(st.nextToken());
//				}
//
//			}});
		
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(2,false));
		topLevel.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,numcols,1));

		Label iLbl = new Label(topLevel,SWT.CENTER);
		iLbl.setText("Additional Import Statements");
		iLbl.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,2,1));
		
		
		final List interfaceList = new List(topLevel,SWT.SINGLE);
		interfaceList.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true));
		Composite right = new Composite(topLevel, SWT.NONE);
		right.setLayout(new GridLayout(1,false));

		Button addBtn = new Button(right,SWT.PUSH);
		addBtn.setText("Add");

		IWorkbench iworkbench = PlatformUI.getWorkbench();
		if (iworkbench == null){System.out.println("no i workbench"); return;}
		IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
		if (iworkbenchwindow == null){System.out.println("no i workbenchwindows"); return;}
		IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
		if (iworkbenchpage == null){System.out.println("no i workbenchpage"); return;}

		final IProject project = getProject(iworkbenchpage);

		addBtn.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {


			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					SelectionDialog dialog = JavaUI.createTypeDialog(getShell(),getWizard().getContainer(),project,IJavaElementSearchConstants.CONSIDER_ALL_TYPES,true);
					
					if (dialog.open() == IDialogConstants.CANCEL_ID)
			            return;

			        Object[] types= dialog.getResult();
			        if (types == null || types.length == 0)
			            return ;
			        
			        for(Object o : types)
			        {
			        	interfaceList.add(o.toString());
			        }
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}});


				Button removeBtn = new Button(right,SWT.PUSH);
				removeBtn.setText("Remove");
				
				removeBtn.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						for(int i : interfaceList.getSelectionIndices())
						{
							interfaceList.remove(i);
						}
						
					}});
		
	}

	
	private void addInterfacePanel(Composite parent,int ncols)
	{
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(2,false));
		topLevel.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,ncols,1));

		Label iLbl = new Label(topLevel,SWT.CENTER);
		iLbl.setText("Additional Implemented Interfaces");
		iLbl.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,2,1));
		
		
		final List interfaceList = new List(topLevel,SWT.SINGLE);
		interfaceList.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true));
		Composite right = new Composite(topLevel, SWT.NONE);
		right.setLayout(new GridLayout(1,false));

		Button addBtn = new Button(right,SWT.PUSH);
		addBtn.setText("Add");

		IWorkbench iworkbench = PlatformUI.getWorkbench();
		if (iworkbench == null){System.out.println("no i workbench"); return;}
		IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
		if (iworkbenchwindow == null){System.out.println("no i workbenchwindows"); return;}
		IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
		if (iworkbenchpage == null){System.out.println("no i workbenchpage"); return;}

		final IProject project = getProject(iworkbenchpage);

		addBtn.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {


			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					SelectionDialog dialog = JavaUI.createTypeDialog(getShell(),getWizard().getContainer(),project,IJavaElementSearchConstants.CONSIDER_INTERFACES,true);
					
					if (dialog.open() == IDialogConstants.CANCEL_ID)
			            return;

			        Object[] types= dialog.getResult();
			        if (types == null || types.length == 0)
			            return ;
			        
			        for(Object o : types)
			        {
			        	interfaceList.add(o.toString());
			        }
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}});


				Button removeBtn = new Button(right,SWT.PUSH);
				removeBtn.setText("Remove");
				
				removeBtn.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						for(int i : interfaceList.getSelectionIndices())
						{
							interfaceList.remove(i);
						}
						
					}});
	}




	private void addCheckPanel(Composite parent, int numcols)
	{
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(2, false));
		topLevel.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,numcols,1));

		
		Button aabtn = new Button(topLevel,SWT.CHECK);
		aabtn.setText("Auto Annotate");
		
		
		Button ppbtn = new Button(topLevel,SWT.CHECK);
		ppbtn.setText("Pretty Print Source");
	}


	public String getPackageName() {
		return packageName;
	}


	public String getClassName() {
		return className;
	}


	public String getSourceOutputLocation() {
		return sourceOutputLocation;
	}


	public LinkedList<Object> getImportStatements() {
		return importStatements;
	}


	public LinkedList<Object> getAdditionalInterfaces() {
		return additionalInterfaces;
	}


	public boolean isAutoAnotate() {
		return autoAnotate;
	}


	public boolean isSourceTransform() {
		return sourceTransform;
	}




	private IProject getProject(IWorkbenchPage iworkbenchpage) {
		IResource res = extractSelection(iworkbenchpage.getSelection());

		if(res == null)
		{
			res = extractResource(iworkbenchpage.getActiveEditor());
		}

		if(res == null){
			return null;
		}
		return res.getProject();
	}

	private IResource extractSelection(ISelection sel) {
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable)element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}

	private IResource extractResource(IEditorPart editor) {
		IEditorInput input = editor.getEditorInput();
		if (!(input instanceof IFileEditorInput))
			return null;
		return ((IFileEditorInput)input).getFile();
	}
}


