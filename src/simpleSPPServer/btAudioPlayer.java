package simpleSPPServer;
import jaco.mp3.player.MP3Player;
import java.io.File;
import java.io.IOException;

public class btAudioPlayer {
	private MP3Player[] players;
	int index = 0,count;
	
	btAudioPlayer(int count, String AudioName){
		assert(count>0);
		this.count = count;
		players = new MP3Player[count];
		for(int i=0; i<count; i++){
			players[i] = new MP3Player(new File(AudioName));			
		}
	}
	public void  play(){		
		index = (index+1)%count;
		players[index].play();
		}
	
/*	
	public static void main(String[] args) throws IOException {
		btAudioPlayer player =  new btAudioPlayer(5);
		System.out.println("initialized");
		for(int i =0; i<5; i++){
			player.play();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
*/
}
