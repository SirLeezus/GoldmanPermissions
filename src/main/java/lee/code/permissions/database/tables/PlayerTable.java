package lee.code.permissions.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lee.code.permissions.enums.Rank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "players")
public class PlayerTable {
  @DatabaseField(id = true, canBeNull = false)
  private UUID uniqueId;

  @DatabaseField(columnName = "permissions")
  private String permissions;

  @DatabaseField(columnName = "rank")
  private String rank;

  public PlayerTable(UUID uniqueId) {
    this.uniqueId = uniqueId;
    this.rank = Rank.DEFAULT.name();
  }
}
