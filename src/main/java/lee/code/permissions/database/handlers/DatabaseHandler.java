package lee.code.permissions.database.handlers;


import lee.code.permissions.database.DatabaseManager;
import lee.code.permissions.database.tables.PlayerTable;

public class DatabaseHandler {
  private final DatabaseManager databaseManager;

  public DatabaseHandler(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  public void createPlayerDatabase(PlayerTable playerTable) {
    databaseManager.createPlayerTable(playerTable);
  }

  public void updatePlayerDatabase(PlayerTable playerTable) {
    databaseManager.updatePlayerTable(playerTable);
  }

  public void deletePlayerDatabase(PlayerTable playerTable) {
    databaseManager.deletePlayerTable(playerTable);
  }
}
