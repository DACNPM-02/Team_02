# Search Service Documentation

## API Routes

### Accommodation Management
- `GET /api/accommodations` - Lấy danh sách tất cả accommodations
- `GET /api/accommodations/{id}` - Lấy thông tin một accommodation theo ID
- `POST /api/accommodations` - Tạo mới accommodation
- `PUT /api/accommodations/{id}` - Cập nhật accommodation
- `DELETE /api/accommodations/{id}` - Xóa accommodation

### Search Functionality
- `GET /api/accommodations/search` - Tìm kiếm nâng cao kết hợp địa lý và từ khóa
  - Parameters:
    - `address`: Địa chỉ cần tìm (bắt buộc)
    - `radius`: Bán kính tìm kiếm (km, mặc định: 10)
    - `limit`: Số lượng kết quả tối đa (mặc định: 10)
    - `query`: Từ khóa tìm kiếm (tùy chọn)
    - `minPrice`: Giá tối thiểu (tùy chọn)
    - `maxPrice`: Giá tối đa (tùy chọn)
    - `numOfBedroom`: Số phòng ngủ (tùy chọn)
    - `numOfBed`: Số giường (tùy chọn)
    - `maxGuests`: Số khách tối đa (tùy chọn)

## Cách hoạt động của Search

### 1. Tìm kiếm theo địa lý
- Sử dụng GeoService để chuyển đổi địa chỉ thành tọa độ (lat, lng)
- Tìm kiếm các accommodations trong bán kính chỉ định sử dụng công thức Haversine
- Kết quả được sắp xếp theo khoảng cách

### 2. Tìm kiếm theo từ khóa
- Sử dụng Elasticsearch để tìm kiếm full-text
- Tìm kiếm trong các trường: title, address, description
- Hỗ trợ tìm kiếm tiếng Việt và phân tích từ khóa

### 3. Kết hợp tìm kiếm
1. Lọc theo địa lý trước:
   - Chuyển đổi địa chỉ thành tọa độ
   - Tìm các accommodations trong bán kính
   - Lấy danh sách ID của các accommodations phù hợp

2. Lọc theo từ khóa và điều kiện:
   - Sử dụng Elasticsearch để tìm kiếm trong danh sách ID đã lọc
   - Áp dụng các bộ lọc về giá, số phòng, số giường, số khách
   - Sắp xếp kết quả theo độ phù hợp

3. Trả về kết quả:
   - Kết hợp thông tin từ MySQL và Elasticsearch
   - Bao gồm khoảng cách và điểm phù hợp
   - Giới hạn số lượng kết quả theo yêu cầu

## Docker Configuration

### Docker Compose
```yaml
version: '3.8'

services:
  search-service:
    build: ./services/search-service
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/rental
      - SPRING_ELASTICSEARCH_URIS=http://elasticsearch:9200
      - GOOGLE_MAPS_API_KEY=your_api_key
    depends_on:
      - mysql
      - elasticsearch

  mysql:
    image: mysql:8.0
    ports:
      - "3306:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=rental
      - MYSQL_USER=rental_user
      - MYSQL_PASSWORD=nhuy0600
    volumes:
      - mysql_data:/var/lib/mysql

  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.12.1
    ports:
      - "9200:9200"
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
    volumes:
      - elasticsearch_data:/usr/share/elasticsearch/data

volumes:
  mysql_data:
  elasticsearch_data:
```

### Cách hoạt động

1. **MySQL Container**:
   - Sử dụng MySQL 8.0
   - Tự động tạo database `rental` và user `rental_user`
   - Dữ liệu được lưu trữ trong volume `mysql_data`

2. **Elasticsearch Container**:
   - Sử dụng Elasticsearch 8.12.1
   - Chạy ở chế độ single-node
   - Tắt bảo mật để dễ dàng phát triển
   - Dữ liệu được lưu trữ trong volume `elasticsearch_data`
   - Sử dụng custom analyzer cho tìm kiếm tiếng Việt

3. **Search Service Container**:
   - Kết nối với MySQL và Elasticsearch thông qua tên service
   - Tự động tạo index Elasticsearch khi khởi động
   - Tự động đồng bộ dữ liệu từ MySQL sang Elasticsearch
   - Tích hợp với Google Maps API cho chuyển đổi địa chỉ

### Cách sử dụng

1. **Khởi động các service**:
```bash
docker-compose up -d
```

2. **Kiểm tra trạng thái**:
```bash
docker-compose ps
```

3. **Xem logs**:
```bash
docker-compose logs -f
```

4. **Dừng các service**:
```bash
docker-compose down
```

### Lưu ý
- Dữ liệu sẽ được tự động đồng bộ từ MySQL sang Elasticsearch khi có thay đổi
- Không cần gọi API sync thủ công
- Các thay đổi trong MySQL sẽ được phản ánh ngay lập tức trong Elasticsearch
- Cần cấu hình Google Maps API Key trong environment variables
- Kết quả tìm kiếm được tối ưu cho cả tìm kiếm địa lý và từ khóa 