/*Manav Kothari
  MPK 170030
  Chat bot using weather and tumblr api
 */
import org.jibble.pircbot.*;

public class ChatMain {
	//main
	public static void main(String[] args) throws Exception{
		//connecting to the server
		ChatBot bot = new ChatBot();
		bot.setVerbose(true);
		bot.connect("irc.freenode.net");
		bot.joinChannel("#CBM");

	}
}
