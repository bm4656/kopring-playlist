## 🎵 플레이리스트 공유 서비스
>
> 인프런 워밍업 3기 미니 프로젝트
>

<img src='https://github.com/user-attachments/assets/9f05bbe2-c5a0-4e8e-9375-69e75efa139c' width='350' height='250' />


## 🍀 테이블 설계
### Member

| 컬럼명 | 데이터 타입 | 제약조건 | Nullable | 설명 |
| --- | --- | --- | --- | --- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |  NOT NULL | 사용자 고유 ID |
| `fake_id`  | UUID | UNIQUE |  NOT NULL | 외부 노출 고유 ID |
| `username` | VARCHAR(64) | UNIQUE |  NOT NULL | 사용자 이름 |
| `password`  | VARCHAR(255) |  |  NOT NULL | 사용자 패스워드 |
| `status` | ENUM(’Y’, ‘N’) | DEFAULT ‘Y’ |  NOT NULL | 상태(정상/삭제) |
| `created_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP |  NOT NULL | 생성 시각 |
| `modified_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | NULL | 수정 시각 |

- Member → Playlist (1:N) 

- 사용자(Member) 와 플레이리스트(Playlist) 간의 관계를 설계할 때, 처음에는 Member 테이블에도 Playlist의 FK를 두는 것을 고려했습니다. 하지만, 이럴 경우 Member가 여러 플레이리스트를 가질 때마다 동일 사용자의 정보가 중복되어 여러 행이 생성됩니다. 따라서 Member가 아닌 Playlist 테이블 쪽에만 Member의 FK를 두는 형태로 진행했습니다.



### Track

| 컬럼명 | 데이터 타입 | 제약조건 | Nullable | 설명 |
| --- | --- | --- | --- | --- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |  NOT NULL | 트랙 고유 ID |
| `artist`  | VARCHAR(255) |  |  NOT NULL | 가수 이름 |
| `title` | VARCHAR(255) |  |  NOT NULL | 노래 제목 |
| `duration`  | VARCHAR(255) |  |  NOT NULL | 음악 길이 |
| `created_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP |  NOT NULL | 생성 시각 |
| `modified_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | NULL | 수정 시각 |



### Track_Playlist

| 컬럼명 | 데이터 타입 | 제약조건 | Nullable | 설명 |
| --- | --- | --- | --- | --- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |  NOT NULL | 고유 ID |
| `playlist_id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |  NOT NULL | 플레이리스트 트랙 고유 ID |
| `track_id`  | BIGINT | FOREIGN KEY REFERENCES track(id) |  NOT NULL | 트랙 고유 ID |
| `created_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP |  NOT NULL | 생성 시각 |
| `modified_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | NULL | 수정 시각 |



### Playlist

| 컬럼명 | 데이터 타입 | 제약조건 | Nullable | 설명 |
| --- | --- | --- | --- | --- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |  NOT NULL | 플레이리스트 고유 ID |
| `host_id`  | BIGINT | FOREIGN KEY REFERENCES member(id) |  NOT NULL | 사용자 고유 ID |
| `title`  | VARCHAR(255) |  |  NOT NULL | 플레이리스트 제목 |
| `playlist_image`  | VARCHAR(255) |  | NULL | 플레이리스트 커버 이미지 |
| `created_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP |  NOT NULL | 생성 시각 |
| `modified_date` | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | NULL | 수정 시각 |

- Playlist <-> Track (N:M) 

- Playlist에 여러 track_id가 물리니 해당 테이블에 같은 id로 여러 행이 반복되었습니다. 따라서 중간 테이블 Track_Playlist를 만들었습니다.



