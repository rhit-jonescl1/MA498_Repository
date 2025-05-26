package dlx_solver;

public class SimpleDLXResultProcessor implements DLXResultProcessor {

	public boolean processResult(DLXResult result) {
		System.out.println(result.toString());
		return true;
	}

}
