package com.lynx.core.game.save;

import java.io.IOException;
import java.nio.file.StandardOpenOption;

import com.lynx.core.exception.LynxException;
import com.lynx.core.exception.SaveException;
import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;
import com.lynx.core.namespace.Namespace;

public class SaveManager {
	
	private static final LynxLogger logger = Loggers.getLogger(SaveManager.class);
	
	private static final String CHECK_SUM = "__checksum";
	
	private Savable savable;
	
	public SaveManager(Savable savable) {
		this.savable = savable;
	}
	
	public void save(Namespace namespace) {
		try {
			Save save = createSave();
			writeSave(namespace, save);
        } catch (IOException e) {
	        logger.error("Save impossible for the namepsace: '{}'", namespace, e);
	        throw new LynxException();
        }
	}

	@SuppressWarnings("resource")
    private void writeSave(Namespace namespace, Save save) throws IOException {
		SaveFile file = new SaveFile(namespace);
		SaveWriter writer = new SaveWriter(file.output(StandardOpenOption.CREATE));
		writer.end(save);
    }

	private Save createSave() {
		Save save = new Save();
		savable.save(save);
		save.write(CHECK_SUM, save.checksum());
	    return save;
    }

	public void load(Namespace namespace) {
		try {
			Save save = readSave(namespace);
			long checksum = (long) save.readAndDelete(CHECK_SUM);
			if (isValidChecksum(save, checksum)) {
				savable.load(save);				
			} else {
				logger.error("Loading save impossible for the namepsace: '{}', checksum invalid!", namespace);
				throw new SaveException();
			}
        } catch (Exception e) {
        	logger.error("Loading save impossible for the namepsace: '{}'", namespace, e);
        	throw new SaveException();
        }
	}

	private boolean isValidChecksum(Save save, long checksum) {
	    return save.checksum() == checksum;
    }

	@SuppressWarnings("resource")
    private Save readSave(Namespace namespace) throws ClassNotFoundException, IOException {
		SaveFile file = new SaveFile(namespace);
		SaveReader reader = new SaveReader(file.input());
		return reader.end();
    }
	
}
