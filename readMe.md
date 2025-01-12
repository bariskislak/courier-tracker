# Courier Service Project

This project efficiently handles **couriers**, calculates **distances**, and uses the **Haversine formula** for
geographic operations. Here's where these components are implemented:

### **Location of Key Components**

1. **Couriers**:
    - Entity Class: `com.example.courierservice.courier.Courier`
    - Repository: `com.example.courierservice.courier.CourierRepository`
    - Service: `com.example.courierservice.courier.CourierService`

2. **Distances**:
    - Distance Calculation Logic: `com.example.courierservice.location.DistanceCalculator`
    - Utilizes the **Haversine formula** to compute distances between geographic coordinates.

3. **Haversine Formula**:
    - Implemented inside `com.example.courierservice.location.DistanceCalculator` utility class.
    - Directly used in geographic queries such as finding nearby couriers.

---

If you need precise details or code updates, let me know! This organization ensures modularity for courier management
and geographic computations.