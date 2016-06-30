package core.key;


public class ContentKey {

	private static final String ID_DELIMITER = ".";

	private FileKey fileKey;
	private String contentId;

	public ContentKey(String contentKey) {
		int lastIdDelimiter = contentKey.lastIndexOf(ID_DELIMITER);
		this.fileKey = new FileKey(contentKey.substring(0, lastIdDelimiter));
		this.contentId = contentKey.substring(lastIdDelimiter + 1);
	}

	public FileKey getFileKey() {
		return this.fileKey;
	}

	public String getContentId() {
		return this.contentId;
	}

	@Override
	public boolean equals(Object o) {
		if ( o == this ) { return true; }

		if ( o instanceof ContentKey ) {
			ContentKey other = (ContentKey) o;
			return this.getFileKey().equals(other.getFileKey())
			        && this.getContentId().equals(other.getContentId());
		}

		return false;
	}

}
