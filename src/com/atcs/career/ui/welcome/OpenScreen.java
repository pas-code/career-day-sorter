//Thomas Varano
//Dec 13, 2018

package com.atcs.career.ui.welcome;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CancellationException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;

/**
 * open an old event
 */
public class OpenScreen {

	public static Event open() throws CancellationException, IOException, ClassNotFoundException {
		File rootDir = new File(FileHandler.SAVE_DIR);
		JFileChooser jfc = new JFileChooser(rootDir, new DirectoryRestrictedFileSystemView(rootDir));
		jfc.setDialogTitle("Open an Event File");
		for (int i = 0; i < 3; i++)
			jfc.remove(0);
		for (Component c : jfc.getComponents())
			System.out.println(c);
		jfc.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getAbsolutePath().toLowerCase().endsWith(".event");
			}

			@Override
			public String getDescription() {
				return ".event";
			}
			
		});
		jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
		jfc.showOpenDialog(null);
		if (jfc.getSelectedFile() == null)
			throw new CancellationException();
		return FileHandler.load(jfc.getSelectedFile().getAbsolutePath());
	}

	public static void main(String[] args) {
		try {
			OpenScreen.open();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private static class DirectoryRestrictedFileSystemView extends FileSystemView {
		private final File[] rootDirectories;

		public DirectoryRestrictedFileSystemView(File rootDirectory) {
			this.rootDirectories = new File[]{rootDirectory};
		}

		@Override
		public File createNewFolder(File containingDir) throws IOException {
			throw new UnsupportedOperationException("Unable to create directory");
		}

		@Override
		public File[] getRoots() {
			return rootDirectories;
		}

		@Override
		public boolean isRoot(File file) {
			for (File root : rootDirectories) {
				if (root.equals(file)) {
					return true;
				}
			}
			return false;
		}
		@Override
		public File getDefaultDirectory() {
			return rootDirectories[0];
		}
		@Override 
		public Boolean isTraversable(File f) {
			return false;
		}
	}
}
