package actions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import menus.Action;
import menus.Menu;
import ui.Report;
import wholesalebooks.Database;

public class SalesPerfomanceReport implements Action {

	@Override
	public boolean execute() {

		try {

			// INPUT:
			String sql = "SELECT orderdate AS start_date, orderdate AS end_date FROM shoporder;";
			ResultSet shopOrder = Database.executeQuery(sql);

			if (shopOrder != null && shopOrder.next()) {
				Map<String, Object> dates = Menu.promptForValues(shopOrder.getMetaData());

				String startDate = (String) dates.get("start_date");
				String endDate = (String) dates.get("end_date");

				System.out.println(startDate + " " + endDate);

				// OUTPUT:
				sql = String.format("SELECT * FROM sales_perm_report ('%s', '%s');", startDate, endDate);
				ResultSet salesReport = Database.executeQuery(sql);

				if (salesReport != null && salesReport.next()) {
					Report.showResultSet(
							String.format(
									"Sales Perfomance Report: %s to %s",
									startDate, endDate
							),
							salesReport
					);
					return true;
				}
			}

		} catch (SQLException ex) {
			if (ex.getMessage() != null) {
				System.out.println(ex.getMessage());
			}
		}

		return false;

	}

}