package depchemobile.com.bod.checkdeposit.fragment;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);
}
