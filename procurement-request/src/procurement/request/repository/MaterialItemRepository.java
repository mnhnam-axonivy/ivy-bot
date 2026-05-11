package procurement.request.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.ivy.environment.Ivy;
import procurement.request.model.MaterialItem;

public class MaterialItemRepository {

  private static MaterialItemRepository instance;

  private MaterialItemRepository() {
  }

  public static MaterialItemRepository getInstance() {
    if (instance == null) {
      instance = new MaterialItemRepository();
    }
    return instance;
  }

  public MaterialItem save(MaterialItem item) {
    if (item == null) {
      throw new IllegalArgumentException("MaterialItem cannot be null");
    }
    Ivy.repo().save(item);
    return item;
  }

  public List<MaterialItem> findAll() {
    return Ivy.repo().search(MaterialItem.class).execute().getAll();
  }

  public MaterialItem findById(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    }
    List<MaterialItem> results = Ivy.repo().search(MaterialItem.class)
        .textField("id").isEqualToIgnoringCase(id)
        .execute().getAll();
    return (results == null || results.isEmpty()) ? null : results.get(0);
  }

  public List<MaterialItem> findByMaterialTypeId(String materialTypeId) {
    if (StringUtils.isBlank(materialTypeId)) {
      return new ArrayList<>();
    }
    return Ivy.repo().search(MaterialItem.class)
        .textField("materialTypeId").isEqualToIgnoringCase(materialTypeId)
        .execute().getAll();
  }

  public List<MaterialItem> searchByDescription(String keyword) {
    if (StringUtils.isBlank(keyword)) {
      return new ArrayList<>();
    }
    return Ivy.repo().search(MaterialItem.class)
        .textField("materialDescription").containsAllWordPatterns(keyword)
        .execute().getAll();
  }

  public void delete(String id) {
    MaterialItem existing = findById(id);
    if (existing != null) {
      Ivy.repo().delete(existing);
    }
  }
}
