package nz.ac.massey.take.takeep.editor;

import java.util.ArrayList;

import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;


public class TakeDocumentProvider extends FileDocumentProvider {



	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			ArrayList<String> strings = new ArrayList<String>();
			for(TAKE_PARTITIONS tp : TAKE_PARTITIONS.values())
			{
				strings.add(tp.name());
			}

			IDocumentPartitioner partitioner =
				new FastPartitioner(
					new TakePartitionScanner(),strings.toArray(new String[strings.size()]));

			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}


	
}
