package sonet.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sonet.core.exceptions.ImportFileException;
import sonet.core.exceptions.UnnamedDBException;

/**
 * 
 */
public class NetworkManager {

  /** The network manager. */
  private String _filename = "";

  /** The current network. */
  private Network _network = new Network();

  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws UnnamedDBException
   */
  public void save() throws IOException, FileNotFoundException, UnnamedDBException {
    // if (!_changed) return;
    if (_filename == null || _filename.equals(""))
      throw new UnnamedDBException();
    try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
      oos.writeObject(_network);
      _network.setChanged(false);
    }
  }

  /**
   * Save contents in 'filename'
   * 
   * @param filename
   * @throws IOException
   * @throws UnnamedDBException
   */
  public void saveAs(String filename) throws IOException, UnnamedDBException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
    _filename = filename;
    try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
      _network = (Network) ois.readObject();
      _network.setChanged(false);
    }
  }

  /**
   * @param filename
   * @throws ImportFileException
   */
  public void importFile(String filename) throws ImportFileException {
    _network.importFile(filename);
  }

  /**
   * @return filename
   */
  public String getFilename() {
    return _filename;
  }

  /**
   * @param filename
   */
  public void setFilename(String filename) {
    _filename = filename;
  }

  /**
   * @return changed?
   */
  public boolean changed() {
    return _network.hasChanged();
  }

  /**
   * @return network
   */
  public Network getNetwork() {
    return _network;
  }

  /**
   * Reset the network.
   */
  public void reset() {
    _network = new Network();
    _filename = null;
  }

}
