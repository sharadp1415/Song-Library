/**
 * @author: Naman Bajaj and Sharad Prasad
 */

//CHANGES 

package view;

//CHANGES

//sharad's changes

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListController {

	@FXML
	ListView<Song> listView;
	@FXML
	TextField namefield;
	@FXML
	TextField artistfield;
	@FXML
	TextField albumfield;
	@FXML
	TextField yearfield;

	@FXML
	Button addbutton;
	@FXML
	Button editbutton;
	@FXML
	Button deletebutton;

	@FXML
	Text entersonginfotext;
	@FXML
	Button songenterok;
	@FXML
	Button songentercancel;

	@FXML
	Text editsonginfotext;
	@FXML
	Button editsavebutton;
	@FXML
	Button editcancelbutton;

	@FXML
	Text deletethissongtext;
	@FXML
	Button deleteyes;
	@FXML
	Button deleteno;

	private ObservableList<Song> obsList;

	int selectedIndex = -1;
	int size = 0;

	public void start(Stage mainStage) throws FileNotFoundException, IOException {
		ArrayList<Song> SongList = new ArrayList<>();

		// read Songs
		Scanner scanner = new Scanner(new File("src/data/songs.txt"));
		// selectedIndex = Integer.parseInt(scanner.nextLine());
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] array = line.split("\\|");
			// System.out.println(Arrays.toString(array));

			Song load = new Song();

			if (array.length == 2)
				load = new Song(array[0], array[1]);
			else if (array.length == 3) {
				try {
					int year = Integer.parseInt(array[2]);
					load = new Song(array[0], array[1], year);
				} catch (NumberFormatException e) {
					load = new Song(array[0], array[1], array[2]);
				}
			} else if (array.length == 4) {
				load = new Song(array[0], array[1], array[2], Integer.parseInt(array[3]));
			}
			SongList.add(load);
			size++;
		}
		scanner.close();

		// hard coded Songs
		// ArrayList<Song> arraylist = new ArrayList<>(
		// Arrays.asList(
		// new Song("Hurricane", "Kanye West", "Donda", 2021),
		// new Song("Immortal", "J. Cole", "4 Your Eyes Only", 2017),
		// new Song("Swim Good", "Frank Ocean", "Swim Good", 2012),
		// new Song("Empty", "Juice Wrld", "Death Race For Love", 2018),
		// new Song("Song 5", "Artist 5"),
		// new Song("Song 4", "Artist 4", 2000),
		// new Song("Song 3", "Artist 3", "No Year"),
		// new Song("Gasoline", "The Weeknd", "Dawn FM", 2022),
		// new Song("Pushing P", "Gunna", "DS4Ever", 2022)
		// ));

		obsList = FXCollections.observableArrayList(SongList);
		System.out.println(SongList.size());
		listView.setItems(obsList.sorted());
		listView.getSelectionModel().select(0);

		Song value = listView.getSelectionModel().getSelectedItem();
		System.out.println(value);
		if (value != null) {
			namefield.setText(value.name);
			artistfield.setText(value.artist);

			if (value.album == null && value.year == -1) {
				albumfield.setText("");
				yearfield.setText("");
			}

			else if (value.album == null) {
				albumfield.setText("");
				yearfield.setText(Integer.toString(value.year));
			}

			else if (value.year == -1) {
				albumfield.setText(value.album);
				yearfield.setText("");
			}

			else {
				albumfield.setText(value.album);
				yearfield.setText(Integer.toString(value.year));
			}
		}

		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem());
	}

	private void setFieldsBlank() {
		namefield.setText("");
		artistfield.setText("");
		albumfield.setText("");
		yearfield.setText("");
	}

	private void showItem() {
		Song value = listView.getSelectionModel().getSelectedItem();
		System.out.println(value);
		if (value != null) {
			namefield.setText(value.name);
			artistfield.setText(value.artist);

			if (value.album == null && value.year == -1) {
				albumfield.setText("");
				yearfield.setText("");
			}

			else if (value.album == null) {
				albumfield.setText("");
				yearfield.setText(Integer.toString(value.year));
			}

			else if (value.year == -1) {
				albumfield.setText(value.album);
				yearfield.setText("");
			}

			else {
				albumfield.setText(value.album);
				yearfield.setText(Integer.toString(value.year));
			}
		} else {
			setFieldsBlank();
		}

	}

	private void showWarningMissing(int arg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Missing Item Warning");
		if (arg == 0)
			alert.setHeaderText("Missing Song Name");
		else if (arg == 1)
			alert.setHeaderText("Missing Song Artist");
		else if (arg == 2)
			alert.setHeaderText("Missing Song Name and Artist");
		alert.showAndWait();
		System.out.println("DONE");
		setFieldsBlank();
	}

	private void showWarningDuplicate() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Missing Item Warning");
		alert.setHeaderText("Duplicate Song and Artist Present");
		alert.showAndWait();
		System.out.println("DONE");
		setFieldsBlank();
	}

	private void showWarningNoSongSelected() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("No Song Selected Warning");
		alert.setHeaderText("No Song Selected to be Edited/Deleted");
		alert.showAndWait();
		System.out.println("DONE");
		setFieldsBlank();
	}

	public void addSong(ActionEvent e) {
		System.out.println("ADD Song");

		int indexofselected = listView.getSelectionModel().getSelectedIndex();

		listView.getSelectionModel().clearSelection();

		namefield.setText("");
		artistfield.setText("");
		albumfield.setText("");
		yearfield.setText("");

		namefield.setEditable(true);
		artistfield.setEditable(true);
		albumfield.setEditable(true);
		yearfield.setEditable(true);

		entersonginfotext.setVisible(true);
		songenterok.setVisible(true);
		songentercancel.setVisible(true);

		songentercancel.setOnAction((ActionEvent a) -> {
			listView.getSelectionModel().select(0);
			entersonginfotext.setVisible(false);
			songenterok.setVisible(false);
			songentercancel.setVisible(false);
			namefield.setEditable(false);
			artistfield.setEditable(false);
			albumfield.setEditable(false);
			yearfield.setEditable(false);
		});

		songenterok.setOnAction((ActionEvent a) -> {
			String name = String.valueOf(namefield.getText());
			String artist = String.valueOf(artistfield.getText());
			String album = String.valueOf(albumfield.getText());
			int year = yearfield.getText() != "" ? Integer.valueOf(yearfield.getText()) : -1;

			System.out.println(name);
			System.out.println(artist);
			System.out.println(album);
			System.out.println(year);

			if (name == "" && artist == "") {
				showWarningMissing(2);
				listView.getSelectionModel().select(indexofselected);
			} else if (name == "") {
				showWarningMissing(0);
				listView.getSelectionModel().select(indexofselected);
			} else if (artist == "") {
				showWarningMissing(1);
				listView.getSelectionModel().select(indexofselected);
			} else {
				Song add = new Song();
				if (album == "")
					add = new Song(name, artist, year);
				else if (year == -1)
					add = new Song(name, artist, album);
				else
					add = new Song(name, artist, album, year);

				boolean dup = false;
				for (Song song : obsList) {
					if (song.name.equalsIgnoreCase(add.name) && song.artist.equalsIgnoreCase(add.artist)) {
						showWarningDuplicate();
						listView.getSelectionModel().select(indexofselected);
						showItem();
						dup = true;
						break;
					}
				}

				if (!dup) {
					obsList.add(add);
					listView.setItems(listView.getItems().sorted());
					listView.getSelectionModel().select(add);

					// adding Song to Songs.txt
					System.out.println("adding Song");
					try {
						System.out.println(obsList.size());
						FileWriter fw = new FileWriter(new File("src/data/Songs.txt"), true);
						if (obsList.size() == 1)
							fw.write(add.toString());
						else
							fw.write("\n" + add.toString());
						fw.flush();
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			entersonginfotext.setVisible(false);
			songenterok.setVisible(false);
			songentercancel.setVisible(false);
			namefield.setEditable(false);
			artistfield.setEditable(false);
			albumfield.setEditable(false);
			yearfield.setEditable(false);
		});
	}

	public void editSong(ActionEvent e) {
		System.out.println("EDIT Song");
		Song selected = listView.getSelectionModel().getSelectedItem();
		System.out.println("SELECTED" + selected);
		if (selected == null) {
			showWarningNoSongSelected();
			return;
		}
		int indexofselected = listView.getSelectionModel().getSelectedIndex();
		listView.setDisable(true);

		editsonginfotext.setVisible(true);
		editsavebutton.setVisible(true);
		editcancelbutton.setVisible(true);

		namefield.setEditable(true);
		artistfield.setEditable(true);
		albumfield.setEditable(true);
		yearfield.setEditable(true);

		editcancelbutton.setOnAction((ActionEvent a) -> {
			namefield.setText(selected.name);
			artistfield.setText(selected.artist);
			albumfield.setText(selected.album);

			String year = Integer.toString(selected.year);
			if (year.equals("-1"))
				yearfield.setText("");
			else
				yearfield.setText(year);

			listView.setDisable(false);
			listView.getSelectionModel().select(indexofselected);

			editsonginfotext.setVisible(false);
			editsavebutton.setVisible(false);
			editcancelbutton.setVisible(false);

			namefield.setEditable(false);
			artistfield.setEditable(false);
			albumfield.setEditable(false);
			yearfield.setEditable(false);
		});

		editsavebutton.setOnAction((ActionEvent a) -> {
			String newname = String.valueOf(namefield.getText());
			String newartist = String.valueOf(artistfield.getText());
			String newalbum = String.valueOf(albumfield.getText());
			int newyear = yearfield.getText() != "" ? Integer.valueOf(yearfield.getText()) : -1;

			if (newname == "" && newartist == "") {
				showWarningMissing(2);
				listView.getSelectionModel().select(indexofselected);
				namefield.setText(selected.name);
				artistfield.setText(selected.artist);
				albumfield.setText(selected.album);

				String year = Integer.toString(selected.year);
				if (year.equals("-1"))
					yearfield.setText("");
				else
					yearfield.setText(year);

			} else if (newname == "") {
				showWarningMissing(0);
				listView.getSelectionModel().select(indexofselected);
				namefield.setText(selected.name);
				artistfield.setText(selected.artist);
				albumfield.setText(selected.album);

				String year = Integer.toString(selected.year);
				if (year.equals("-1"))
					yearfield.setText("");
				else
					yearfield.setText(year);
			} else if (newartist == "") {
				showWarningMissing(1);
				listView.getSelectionModel().select(indexofselected);
				namefield.setText(selected.name);
				artistfield.setText(selected.artist);
				albumfield.setText(selected.album);

				String year = Integer.toString(selected.year);
				if (year.equals("-1"))
					yearfield.setText("");
				else
					yearfield.setText(year);
			} else {
				Song replace = new Song();
				if (newalbum == "")
					replace = new Song(newname, newartist, newyear);
				else if (newyear == -1)
					replace = new Song(newname, newartist, newalbum);
				else
					replace = new Song(newname, newartist, newalbum, newyear);

				boolean dup = false;
				for (Song song : obsList) {
					if (song.name.equals(replace.name) && song.artist.equals(replace.artist)) {
						if (song.name.equals(replace.name) && song.artist.equals(replace.artist)) {
							showWarningDuplicate();
							dup = true;
							break;
						}
					}
				}

				if (!dup) {
					obsList.remove(selected);
					obsList.add(replace);
					listView.setItems(listView.getItems().sorted());
					listView.getSelectionModel().select(replace);

					try {
						File Songs = new File("src/data/Songs.txt");
						FileWriter fw = new FileWriter(Songs, false);

						boolean firstSong = true;
						for (Song Song : obsList) {
							if (!Song.toString().equals(selected.toString()))
								if (firstSong) {
									fw.write(Song.toString());
									firstSong = false;
								} else
									fw.write("\n" + Song.toString());
						}

						fw.flush();
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				if (dup) {
					namefield.setText(selected.name);
					artistfield.setText(selected.artist);
					albumfield.setText(selected.album);

					String year = Integer.toString(selected.year);
					if (year.equals("-1"))
						yearfield.setText("");
					else
						yearfield.setText(year);

					listView.getSelectionModel().select(indexofselected);
				}
			}

			// String year = Integer.toString(selected.year);
			// if(year.equals("-1"))
			// yearfield.setText("");
			// else
			// yearfield.setText(year);

			listView.setDisable(false);

			editsonginfotext.setVisible(false);
			editsavebutton.setVisible(false);
			editcancelbutton.setVisible(false);

			namefield.setEditable(false);
			artistfield.setEditable(false);
			albumfield.setEditable(false);
			yearfield.setEditable(false);
		});

	}

	public void deleteSong(ActionEvent e) {
		System.out.println("DELETE Song");

		Song selected = listView.getSelectionModel().getSelectedItem();
		int indexofselected = listView.getSelectionModel().getSelectedIndex();

		if (selected == null) {
			showWarningNoSongSelected();
			return;
		}

		System.out.println("SELECTED " + selected);

		addbutton.setVisible(false);
		editbutton.setVisible(false);
		deletebutton.setVisible(false);

		deletethissongtext.setText("Delete " + selected.name + " by " + selected.artist + "?");
		deletethissongtext.setVisible(true);

		deleteyes.setVisible(true);
		deleteno.setVisible(true);

		deleteno.setOnAction((ActionEvent a) -> {
			addbutton.setVisible(true);
			editbutton.setVisible(true);
			deletebutton.setVisible(true);
			deletethissongtext.setVisible(false);
			deleteyes.setVisible(false);
			deleteno.setVisible(false);
		});

		deleteyes.setOnAction((ActionEvent a) -> {
			obsList.remove(selected);
			listView.getSelectionModel().select(indexofselected);
			try {
				File Songs = new File("src/data/Songs.txt");
				FileWriter fw = new FileWriter(Songs, false);

				boolean firstSong = true;
				for (Song Song : obsList) {
					if (!Song.toString().equals(selected.toString()))
						if (firstSong) {
							fw.write(Song.toString());
							firstSong = false;
						} else
							fw.write("\n" + Song.toString());
				}

				fw.flush();
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			addbutton.setVisible(true);
			editbutton.setVisible(true);
			deletebutton.setVisible(true);
			deletethissongtext.setVisible(false);
			deleteyes.setVisible(false);
			deleteno.setVisible(false);

			showItem();
		});
	}

}