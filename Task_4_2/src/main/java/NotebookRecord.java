import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * моя простая реализация того, как может выглядеть запись в блокноте
 */
public class NotebookRecord {

    private final String title;
    private final String text;
    private final Date createdAt;

    public NotebookRecord(@NotNull String title, @NotNull String text, @NotNull Date date){
        this.title = title;
        this.text = text;
        this.createdAt = date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getCreationDate() {
        return createdAt;
    }
}
