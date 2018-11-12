package loanbook.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import loanbook.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String password;
    private String defaultEmail;
    private Path loanBookFilePath = Paths.get("data" , "loanbook.xml");
    private String passwordSalt;

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
        passwordSalt = Password.getSalt();
        password = (new Password("a12345", passwordSalt)).hashedPassword(); // Default password is set to a12345
        defaultEmail = "default";
    }

    public UserPrefs(String salt) {
        this();
        passwordSalt = salt;
        password = (new Password("a12345", passwordSalt)).hashedPassword();
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getLoanBookFilePath() {
        return loanBookFilePath;
    }

    public void setLoanBookFilePath(Path loanBookFilePath) {
        this.loanBookFilePath = loanBookFilePath;
    }

    public void setPass(Password pass) {
        password = pass.hashedPassword();
    }

    public String getSalt() {
        return passwordSalt;
    }

    public String getPass() {
        return password;
    }

    public void setDefaultEmail(String email) {
        this.defaultEmail = email;
    }

    public String getDefaultEmail() {
        return defaultEmail;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(password, o.password)
                && Objects.equals(defaultEmail, o.defaultEmail)
                && Objects.equals(loanBookFilePath, o.loanBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, loanBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("Password : " + password);
        sb.append("My email address : " + defaultEmail);
        sb.append("\nLocal data file location : " + loanBookFilePath);
        return sb.toString();
    }

}
