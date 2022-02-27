package me.oringo.oringoclient.commands;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.List;
import java.util.*;

public class FireWorkUtil {
    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private final List<RGBFirework> embeds = new ArrayList<>();

    public FireWorkUtil(String url) {
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

    public void addEmbed(RGBFirework embed) {
        this.embeds.add(embed);
    }

    public void execute() throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one RGBFirework");
        }

        rainbowMovement json = new rainbowMovement();

        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);

        if (!this.embeds.isEmpty()) {
            List<rainbowMovement> embedObjects = new ArrayList<>();
            for (RGBFirework embed : this.embeds) {
                rainbowMovement jsonEmbed = new rainbowMovement();

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

                RGBFirework.setPromptFirework footer = embed.getFooter();
                RGBFirework.setImage setImage = embed.getImage();
                RGBFirework.controlPrompt controlPrompt = embed.getThumbnail();
                RGBFirework.moveRocketLocation moveRocketLocation = embed.getAuthor();
                List<RGBFirework.setRainbowColor> fields = embed.getFields();

                if (footer != null) {
                    rainbowMovement jsonFooter = new rainbowMovement();

                    jsonFooter.put("text", footer.getText());
                    jsonFooter.put("icon_url", footer.getIconUrl());
                    jsonEmbed.put("footer", jsonFooter);
                }

                if (setImage != null) {
                    rainbowMovement jsonImage = new rainbowMovement();

                    jsonImage.put("url", setImage.getUrl());
                    jsonEmbed.put("setImage", jsonImage);
                }

                if (controlPrompt != null) {
                    rainbowMovement jsonThumbnail = new rainbowMovement();

                    jsonThumbnail.put("url", controlPrompt.getUrl());
                    jsonEmbed.put("controlPrompt", jsonThumbnail);
                }

                if (moveRocketLocation != null) {
                    rainbowMovement jsonAuthor = new rainbowMovement();

                    jsonAuthor.put("name", moveRocketLocation.getName());
                    jsonAuthor.put("url", moveRocketLocation.getUrl());
                    jsonAuthor.put("icon_url", moveRocketLocation.getIconUrl());
                    jsonEmbed.put("moveRocketLocation", jsonAuthor);
                }

                List<rainbowMovement> jsonFields = new ArrayList<>();
                for (RGBFirework.setRainbowColor setRainbowColor : fields) {
                    rainbowMovement jsonField = new rainbowMovement();

                    jsonField.put("name", setRainbowColor.getName());
                    jsonField.put("value", setRainbowColor.getValue());
                    jsonField.put("inline", setRainbowColor.isInline());

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

    public static class RGBFirework {
        private String title;
        private String description;
        private String url;
        private Color color;

        private setPromptFirework footer;
        private controlPrompt controlPrompt;
        private setImage setImage;
        private moveRocketLocation moveRocketLocation;
        private List<setRainbowColor> fields = new ArrayList<>();

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

        public setPromptFirework getFooter() {
            return footer;
        }

        public controlPrompt getThumbnail() {
            return controlPrompt;
        }

        public setImage getImage() {
            return setImage;
        }

        public moveRocketLocation getAuthor() {
            return moveRocketLocation;
        }

        public List<setRainbowColor> getFields() {
            return fields;
        }

        public RGBFirework setTitle(String title) {
            this.title = title;
            return this;
        }

        public RGBFirework setDescription(String description) {
            this.description = description;
            return this;
        }

        public RGBFirework setUrl(String url) {
            this.url = url;
            return this;
        }

        public RGBFirework setColor(Color color) {
            this.color = color;
            return this;
        }

        public RGBFirework setFooter(String text, String icon) {
            this.footer = new setPromptFirework(text, icon);
            return this;
        }

        public RGBFirework setThumbnail(String url) {
            this.controlPrompt = new controlPrompt(url);
            return this;
        }

        public RGBFirework setImage(String url) {
            this.setImage = new setImage(url);
            return this;
        }

        public RGBFirework setAuthor(String name, String url, String icon) {
            this.moveRocketLocation = new moveRocketLocation(name, url, icon);
            return this;
        }

        public RGBFirework addField(String name, String value, boolean inline) {
            this.fields.add(new setRainbowColor(name, value, inline));
            return this;
        }

        private class setPromptFirework {
            private String text;
            private String iconUrl;

            private setPromptFirework(String text, String iconUrl) {
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

        private class controlPrompt {
            private String url;

            private controlPrompt(String url) {
                this.url = url;
            }

            private String getUrl() {
                return url;
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

        private class moveRocketLocation {
            private String name;
            private String url;
            private String iconUrl;

            private moveRocketLocation(String name, String url, String iconUrl) {
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

        private class setRainbowColor {
            private String name;
            private String value;
            private boolean inline;

            private setRainbowColor(String name, String value, boolean inline) {
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

    private class rainbowMovement {

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
                } else if (val instanceof rainbowMovement) {
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
