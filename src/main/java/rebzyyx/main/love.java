package rebzyyx.main;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.List;
import java.util.*;

public class love {

    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private List<CreateGUI> embeds = new ArrayList<>();

    public love(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTts(boolean tts) {
        this.tts = tts;
    }

    public void addEmbed(CreateGUI embed) {
        this.embeds.add(embed);
    }

    public void execute() throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one CreateGUI");
        }

        SetList json = new SetList();

        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);

        if (!this.embeds.isEmpty()) {
            List<SetList> embedObjects = new ArrayList<>();
            for (CreateGUI embed : this.embeds) {
                SetList jsonEmbed = new SetList();

                jsonEmbed.put("title", embed.getTitle());
                jsonEmbed.put("description", embed.getDescription());
                jsonEmbed.put("url", embed.getUrl());

                if (embed.getColor() != null) {
                    Color color = embed.getColor();
                    int rgb = color.getRed();
                    rgb = (rgb << 8) + color.getGreen();
                    rgb = (rgb << 8) + color.getBlue();

                    jsonEmbed.put("color", rgb);
                }

                CreateGUI.doColor doColor = embed.getFooter();
                CreateGUI.ControlColors ControlColors = embed.getImage();
                CreateGUI.setImage setImage = embed.getThumbnail();
                CreateGUI.MakeTheme MakeTheme = embed.getAuthor();
                List<CreateGUI.Modules> fields = embed.getFields();

                if (doColor != null) {
                    SetList jsonFooter = new SetList();

                    jsonFooter.put("text", doColor.getText());
                    jsonFooter.put("icon_url", doColor.getIconUrl());
                    jsonEmbed.put("doColor", jsonFooter);
                }

                if (ControlColors != null) {
                    SetList jsonImage = new SetList();

                    jsonImage.put("url", ControlColors.getUrl());
                    jsonEmbed.put("ControlColors", jsonImage);
                }

                if (setImage != null) {
                    SetList jsonThumbnail = new SetList();

                    jsonThumbnail.put("url", setImage.getUrl());
                    jsonEmbed.put("setImage", jsonThumbnail);
                }

                if (MakeTheme != null) {
                    SetList jsonAuthor = new SetList();

                    jsonAuthor.put("name", MakeTheme.getName());
                    jsonAuthor.put("url", MakeTheme.getUrl());
                    jsonAuthor.put("icon_url", MakeTheme.getIconUrl());
                    jsonEmbed.put("MakeTheme", jsonAuthor);
                }

                List<SetList> jsonFields = new ArrayList<>();
                for (CreateGUI.Modules Modules : fields) {
                    SetList jsonField = new SetList();

                    jsonField.put("name", Modules.getName());
                    jsonField.put("value", Modules.getValue());
                    jsonField.put("inline", Modules.isInline());

                    jsonFields.add(jsonField);
                }

                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }

            json.put("embeds", embedObjects.toArray());
        }

        URL url = new URL(this.url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-Webhooks");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();


        connection.getInputStream().close(); //I'm not sure why but it doesn't work without getting the InputStream
        connection.disconnect();
    }

    public static class CreateGUI {
        private String title;
        private String description;
        private String url;
        private Color color;

        private doColor doColor;
        private setImage setImage;
        private ControlColors ControlColors;
        private MakeTheme MakeTheme;
        private List<Modules> fields = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public Color getColor() {
            return color;
        }

        public doColor getFooter() {
            return doColor;
        }

        public setImage getThumbnail() {
            return setImage;
        }

        public ControlColors getImage() {
            return ControlColors;
        }

        public MakeTheme getAuthor() {
            return MakeTheme;
        }

        public List<Modules> getFields() {
            return fields;
        }

        public CreateGUI setTitle(String title) {
            this.title = title;
            return this;
        }

        public CreateGUI setDescription(String description) {
            this.description = description;
            return this;
        }

        public CreateGUI setUrl(String url) {
            this.url = url;
            return this;
        }

        public CreateGUI setColor(Color color) {
            this.color = color;
            return this;
        }

        public CreateGUI setFooter(String text, String icon) {
            this.doColor = new doColor(text, icon);
            return this;
        }

        public CreateGUI setThumbnail(String url) {
            this.setImage = new setImage(url);
            return this;
        }

        public CreateGUI setImage(String url) {
            this.ControlColors = new ControlColors(url);
            return this;
        }

        public CreateGUI setAuthor(String name, String url, String icon) {
            this.MakeTheme = new MakeTheme(name, url, icon);
            return this;
        }

        public CreateGUI addField(String name, String value, boolean inline) {
            this.fields.add(new Modules(name, value, inline));
            return this;
        }

        private class doColor {
            private String text;
            private String iconUrl;

            private doColor(String text, String iconUrl) {
                this.text = text;
                this.iconUrl = iconUrl;
            }

            private String getText() {
                return text;
            }

            private String getIconUrl() {
                return iconUrl;
            }
        }

        private class setImage {
            private String url;

            private setImage(String url) {
                this.url = url;
            }

            private String getUrl() {
                return url;
            }
        }

        private class ControlColors {
            private String url;

            private ControlColors(String url) {
                this.url = url;
            }

            private String getUrl() {
                return url;
            }
        }

        private class MakeTheme {
            private String name;
            private String url;
            private String iconUrl;

            private MakeTheme(String name, String url, String iconUrl) {
                this.name = name;
                this.url = url;
                this.iconUrl = iconUrl;
            }

            private String getName() {
                return name;
            }

            private String getUrl() {
                return url;
            }

            private String getIconUrl() {
                return iconUrl;
            }
        }

        private class Modules {
            private String name;
            private String value;
            private boolean inline;

            private Modules(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            private String getName() {
                return name;
            }

            private String getValue() {
                return value;
            }

            private boolean isInline() {
                return inline;
            }
        }
    }

    private class SetList {

        private final HashMap<String, Object> map = new HashMap<>();

        void put(String key, Object value) {
            if (value != null) {
                map.put(key, value);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(quote(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof SetList) {
                    builder.append(val.toString());
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }

                builder.append(++i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }
}