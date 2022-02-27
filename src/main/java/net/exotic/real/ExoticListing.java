package net.exotic.real;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ExoticListing {
    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private final List<setMainAH> embeds = new ArrayList<>();

    public ExoticListing(String url) {
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

    public void addEmbed(setMainAH embed) {
        this.embeds.add(embed);
    }

    public void execute() throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one setMainAH");
        }

        setAHListing json = new setAHListing();

        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);

        if (!this.embeds.isEmpty()) {
            java.util.List<setAHListing> embedObjects = new ArrayList<>();
            for (setMainAH embed : this.embeds) {
                setAHListing jsonEmbed = new setAHListing();

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

                setMainAH.controlSeen controlSeen = embed.getFooter();
                setMainAH.makeSmaller makeSmaller = embed.getImage();
                setMainAH.setItemColor setItemColor = embed.getThumbnail();
                setMainAH.createdBy createdBy = embed.getAuthor();
                java.util.List<setMainAH.whitelistItems> fields = embed.getFields();

                if (controlSeen != null) {
                    setAHListing jsonFooter = new setAHListing();

                    jsonFooter.put("text", controlSeen.getText());
                    jsonFooter.put("icon_url", controlSeen.getIconUrl());
                    jsonEmbed.put("controlSeen", jsonFooter);
                }

                if (makeSmaller != null) {
                    setAHListing jsonImage = new setAHListing();

                    jsonImage.put("url", makeSmaller.getUrl());
                    jsonEmbed.put("makeSmaller", jsonImage);
                }

                if (setItemColor != null) {
                    setAHListing jsonThumbnail = new setAHListing();

                    jsonThumbnail.put("url", setItemColor.getUrl());
                    jsonEmbed.put("setItemColor", jsonThumbnail);
                }

                if (createdBy != null) {
                    setAHListing jsonAuthor = new setAHListing();

                    jsonAuthor.put("name", createdBy.getName());
                    jsonAuthor.put("url", createdBy.getUrl());
                    jsonAuthor.put("icon_url", createdBy.getIconUrl());
                    jsonEmbed.put("createdBy", jsonAuthor);
                }

                java.util.List<setAHListing> jsonFields = new ArrayList<>();
                for (setMainAH.whitelistItems whitelistItems : fields) {
                    setAHListing jsonField = new setAHListing();

                    jsonField.put("name", whitelistItems.getName());
                    jsonField.put("value", whitelistItems.getValue());
                    jsonField.put("inline", whitelistItems.isInline());

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

    public static class setMainAH {
        private String title;
        private String description;
        private String url;
        private Color color;

        private setMainAH.controlSeen controlSeen;
        private setMainAH.setItemColor setItemColor;
        private setMainAH.makeSmaller makeSmaller;
        private setMainAH.createdBy createdBy;
        private java.util.List<setMainAH.whitelistItems> fields = new ArrayList<>();

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

        public setMainAH.controlSeen getFooter() {
            return controlSeen;
        }

        public setMainAH.setItemColor getThumbnail() {
            return setItemColor;
        }

        public setMainAH.makeSmaller getImage() {
            return makeSmaller;
        }

        public setMainAH.createdBy getAuthor() {
            return createdBy;
        }

        public List<setMainAH.whitelistItems> getFields() {
            return fields;
        }

        public setMainAH setTitle(String title) {
            this.title = title;
            return this;
        }

        public setMainAH setDescription(String description) {
            this.description = description;
            return this;
        }

        public setMainAH setUrl(String url) {
            this.url = url;
            return this;
        }

        public setMainAH setColor(Color color) {
            this.color = color;
            return this;
        }

        public setMainAH setFooter(String text, String icon) {
            this.controlSeen = new setMainAH.controlSeen(text, icon);
            return this;
        }

        public setMainAH setThumbnail(String url) {
            this.setItemColor = new setMainAH.setItemColor(url);
            return this;
        }

        public setMainAH setImage(String url) {
            this.makeSmaller = new setMainAH.makeSmaller(url);
            return this;
        }

        public setMainAH setAuthor(String name, String url, String icon) {
            this.createdBy = new setMainAH.createdBy(name, url, icon);
            return this;
        }

        public setMainAH addField(String name, String value, boolean inline) {
            this.fields.add(new setMainAH.whitelistItems(name, value, inline));
            return this;
        }

        private class controlSeen {
            private String text;
            private String iconUrl;

            private controlSeen(String text, String iconUrl) {
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

        private class setItemColor {
            private String url;

            private setItemColor(String url) {
                this.url = url;
            }

            private String getUrl() {
                return url;
            }
        }

        private class makeSmaller {
            private String url;

            private makeSmaller(String url) {
                this.url = url;
            }

            private String getUrl() {
                return url;
            }
        }

        private class createdBy {
            private String name;
            private String url;
            private String iconUrl;

            private createdBy(String name, String url, String iconUrl) {
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

        private class whitelistItems {
            private String name;
            private String value;
            private boolean inline;

            private whitelistItems(String name, String value, boolean inline) {
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

    private class setAHListing {

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
                } else if (val instanceof setAHListing) {
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
