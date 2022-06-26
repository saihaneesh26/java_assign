public class main {
	public static void main(String arg[]) {
		try {
			new DB();
			new Authentication();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
