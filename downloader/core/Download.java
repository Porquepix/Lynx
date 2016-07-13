package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;

public class Download extends Observable implements Runnable {

	private static final int BUFFER_SIZE = 1024;
	
	private URL url;
	private int size;
	private int downloaded;
	private DownloadStatus status;
	
	public Download(URL url) {
		this.url = url;
		this.size = -1;
		this.downloaded = 0;
		this.status = DownloadStatus.PAUSED;
	}
	
	public String getUrl() {
		return this.url.toString();
	}
	
	public int getSize() {
		return this.size;
	}
	
	public float getProgress() {
		return ((float) this.downloaded / this.size) * 100;
	}
	
	public DownloadStatus getStatus() {
		return this.status;
	}
	
	private void setStatus(DownloadStatus status) {
		this.status = status;
		stateChanged();
	}
	
	private void stateChanged() {
		setChanged();
		notifyObservers();
	}
	
	public void pause() {
		setStatus(DownloadStatus.PAUSED);
	}
	
	public void download() {
		setStatus(DownloadStatus.DOWNLOADING);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void cancel() {
		setStatus(DownloadStatus.CANCELLED);
	}
	
	public void error() {
		setStatus(DownloadStatus.ERROR);
	}

	@Override
	public void run() {
		RandomAccessFile file = null;
		InputStream stream = null;
		
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
			connection.connect();
			
			if (connection.getResponseCode() / 100 != 2) {
				error();
			}
			
			int contentLength = connection.getContentLength();
			if (contentLength < 1) {
				error();
			}
			
			if (size == -1) {
				this.size = contentLength;
				stateChanged();
			}
			
			file = new RandomAccessFile(getFileName(url), "rw");
			file.seek(downloaded);
			
			stream = connection.getInputStream();
			while (status == DownloadStatus.DOWNLOADING) {
				byte buffer[];
				if (size - downloaded > BUFFER_SIZE) {
					buffer = new byte[BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}
				
				int read = stream.read(buffer);
				if (read == -1) {
					break;
				}
				
				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}
			
			if (status == DownloadStatus.DOWNLOADING) {
				setStatus(DownloadStatus.COMPLETE);
			}
		} catch (IOException e) {
			e.printStackTrace();
			error();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private String getFileName(URL url) {
		String filename = url.getFile();
		return filename.substring(filename.lastIndexOf('/') + 1);
	}

}
