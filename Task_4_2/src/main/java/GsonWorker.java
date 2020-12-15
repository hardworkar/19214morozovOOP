import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GsonWorker {
    /* достает из существующего? json-а */
    protected MyNotebook deserialize(@NotNull Path file) throws IOException {
        String fileContent = new String(Files.readAllBytes(file));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MyNotebook.class, new MyNotebookDeserializer())
                .setPrettyPrinting()
                .create();

        return gson.fromJson(fileContent, MyNotebook.class);
    }
    /* складывает в json */
    protected void serialize(@NotNull MyNotebook notebook, @NotNull String file){
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(MyNotebook.class, new MyNotebookDeserializer()).setPrettyPrinting()
                    .create();
            gson.toJson(notebook, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyNotebookDeserializer implements JsonDeserializer<MyNotebook>{
    /* очень вкусно, очень классно, никому не советую
     *  так как у меня блокнот использует абстрактные классы, стандартный deserializer из gson-а поднимает лапки и говорит, что не может в такое (ну и в интерфейсы тоже)
     *  https://github.com/google/gson/issues/411  :)))
     *  поэтому приходится изворачиваться и делать свой кастомный :/ */
    @Override
    public MyNotebook deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        MyNotebook result = new MyNotebook();
        result.setName(jsonObject.get("name").getAsString());
        var records = jsonObject.get("records").getAsJsonArray();
        for(var record : records){
            String title = record.getAsJsonObject().get("title").getAsString();
            String text = record.getAsJsonObject().get("text").getAsString();
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss aa", Locale.US);
            try {
                Date createdDate = formatter.parse(record.getAsJsonObject().get("createdAt").getAsString());
                result.records.add(new NotebookRecord(title, text, createdDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}


