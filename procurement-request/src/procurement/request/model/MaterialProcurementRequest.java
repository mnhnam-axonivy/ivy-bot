package procurement.request.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MaterialProcurementRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  private String projectName;
  private String projectNumberCostCenter;
  private String constructionSiteDeliveryAddress;
  private Date requiredDeliveryDate;
  private RequestPriority priority;
  private String requester;
  private String orderNotes;
  private Date requestDate;
  private List<MaterialItem> materialItems;
  private RequestStatus status;
  private Double totalNetAmount;
  private Date createdDate;
  private Date lastModifiedDate;

  public MaterialProcurementRequest() {
    this.id = UUID.randomUUID().toString();
    this.priority = RequestPriority.NORMAL;
    this.status = RequestStatus.DRAFT;
    this.requestDate = new Date();
    this.createdDate = new Date();
    this.lastModifiedDate = new Date();
    this.materialItems = new ArrayList<>();
    this.totalNetAmount = 0.0;
  }

  public void calculateTotalNetAmount() {
    double total = 0.0;
    for (MaterialItem item : materialItems) {
      item.calculateTotal();
      if (item.getTotalPriceNet() != null) {
        total += item.getTotalPriceNet();
      }
    }
    this.totalNetAmount = total;
  }

  public void reindexItems() {
    for (int i = 0; i < materialItems.size(); i++) {
      materialItems.get(i).setPosition(i + 1);
    }
  }

  // Getters and Setters

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getProjectName() { return projectName; }
  public void setProjectName(String projectName) { this.projectName = projectName; }

  public String getProjectNumberCostCenter() { return projectNumberCostCenter; }
  public void setProjectNumberCostCenter(String projectNumberCostCenter) { this.projectNumberCostCenter = projectNumberCostCenter; }

  public String getConstructionSiteDeliveryAddress() { return constructionSiteDeliveryAddress; }
  public void setConstructionSiteDeliveryAddress(String constructionSiteDeliveryAddress) { this.constructionSiteDeliveryAddress = constructionSiteDeliveryAddress; }

  public Date getRequiredDeliveryDate() { return requiredDeliveryDate; }
  public void setRequiredDeliveryDate(Date requiredDeliveryDate) { this.requiredDeliveryDate = requiredDeliveryDate; }

  public RequestPriority getPriority() { return priority; }
  public void setPriority(RequestPriority priority) { this.priority = priority; }

  public String getRequester() { return requester; }
  public void setRequester(String requester) { this.requester = requester; }

  public String getOrderNotes() { return orderNotes; }
  public void setOrderNotes(String orderNotes) { this.orderNotes = orderNotes; }

  public Date getRequestDate() { return requestDate; }
  public void setRequestDate(Date requestDate) { this.requestDate = requestDate; }

  public List<MaterialItem> getMaterialItems() { return materialItems; }
  public void setMaterialItems(List<MaterialItem> materialItems) { this.materialItems = materialItems; }

  public RequestStatus getStatus() { return status; }
  public void setStatus(RequestStatus status) { this.status = status; }

  public Double getTotalNetAmount() { return totalNetAmount; }
  public void setTotalNetAmount(Double totalNetAmount) { this.totalNetAmount = totalNetAmount; }

  public Date getCreatedDate() { return createdDate; }
  public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

  public Date getLastModifiedDate() { return lastModifiedDate; }
  public void setLastModifiedDate(Date lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }
}
