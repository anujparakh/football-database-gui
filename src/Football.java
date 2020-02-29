
public class Football {
	public static MainWindow mw;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("Hello World");

        mw = new MainWindow();
	}
	
	public static void handleUpdatePageDisplay(String pageCode) {
		//	This is where new SQL data will get feeded in
		
		System.out.println("Updating data ..." + pageCode);
		
		mw.updatePageCode(pageCode);
	}

}
