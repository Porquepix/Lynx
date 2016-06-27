package core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContentKey {

	private static final String ID_DELIMITER = ".";

	private String fileId;
	private String contentId;

	public ContentKey(String contentKey) {
		int lastIdDelimiter = contentKey.lastIndexOf(ID_DELIMITER);
		this.fileId = contentKey.substring(0, lastIdDelimiter);
		this.contentId = contentKey.substring(lastIdDelimiter + 1);
	}

	public String getFileId() {
		return this.fileId;
	}

	public Path getFileIdAsPath() {
		return Paths.get(this.fileId.replace(ID_DELIMITER, File.separator));
	}

	public String getContentId() {
		return this.contentId;
	}

	@Override
	public int hashCode() {
		return this.fileId.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if ( o == this ) { return true; }

		if ( o instanceof ContentKey ) {
			ContentKey other = (ContentKey) o;
			return this.getFileId().equals(other.getFileId())
			        && this.getContentId().equals(other.getContentId());
		}

		return false;
	}

}
