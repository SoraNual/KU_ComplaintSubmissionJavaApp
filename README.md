# วิธีการติดต้ังและรันโปรแกรม
ขั้นตอนการติดตั้งโปรแกรม
1. ใช้คำสั่ง git clone https://github.com/SoraNual/KU_ComplaintSubmissionJavaApp.git
2. จากนั้นกดไปที่ KU จะแจ้ง_windows.jar (ในกรณีที่ใช้ระบบปฏิบัติการ windows)

**หมายเหตุ** JDK Version 17

# การวางโครงสร้างไฟล์
โฟลเดอร์ KU_ComplaintSubmissionJavaApp
- data : โฟล์เดอร์ data นี้จะเก็บข้อมูลที่เป็นที่เกี่ยวกับโฟลเดอร์รูปภาพต่างๆ และ ไฟล์ .csv ที่โปรแกรมต้องใช้งาน
  - img : เก็บรูปภาพโปรไฟล์ของผู้ใช้งาน และเรื่องร้องเรียนที่ผู้ใช้แนบมากับเรื่องร้องเรียน
    - complaint : ไฟล์เก็บรูปภาพที่ผู้ใช้ได้แนบรูปมากับเรื่องร้องเรียน
    - รูปภาพของผู้ใช้งาน
  - ไฟล์ .csv ต่างๆ

# สรุปสิ่งที่พัฒนาในแต่ละครั้ง
| ครั้งที่ | รายละเอียด |
| ------ | ----------- |
| 1 | ส่วนใหญ่จะเป็นการวาง Model Class และ Controller Class รวมไปถึงการทำหน้า .fxml |
| 2 | เริ่มมีระบบต่างๆเข้ามา เช่น ระบบล็อกอิน ระบบลงทะเบียน ฯลฯ เป็นต้น |
| 3 | เริ่มมีการนำ Theme เข้ามาใช้กับโปรแกรม เริ่มมีการนำธีมมาผูกกับ fxml |
| 4 | แต่ละฝ่ายทำฝั่งของตัวเองเสร็จ และนำธีมมาผูกกับ view และแก้บั๊กบางส่วนที่เกิดขึ้น |

