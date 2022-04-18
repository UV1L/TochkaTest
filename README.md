# TochkaTest
 
Приложение для поиска пользователей на Github

release apk:  [apk 1.1.zip](https://github.com/UV1L/TochkaTest/files/8504272/apk.1.1.zip)

ver: 1.1

![imgonline-com-ua-Resize-Ozkh6Wi3mU2Su](https://user-images.githubusercontent.com/50074365/163735636-c4279d30-194b-4d27-a8d9-b0cba4e639e4.jpg)
![imgonline-com-ua-Resize-xG2qOISsBHNM6Id](https://user-images.githubusercontent.com/50074365/163735637-10a04dd7-49ca-4f5c-b0e3-52f45916ee9d.jpg)
![imgonline-com-ua-Resize-niLbFeXZe1zIezA](https://user-images.githubusercontent.com/50074365/163735638-57dd3443-c866-4120-8a7d-790b15c1c41a.jpg)
![imgonline-com-ua-Resize-p0Xy2ABU2roc6sR](https://user-images.githubusercontent.com/50074365/163735639-b789ccaf-6731-4b4a-84eb-3dbef3a380a9.jpg)
![imgonline-com-ua-Resize-p1B5V6ENVUDqgN](https://user-images.githubusercontent.com/50074365/163768378-c4ecb840-63d1-4711-bd62-4bcfe0afef31.jpg)
![imgonline-com-ua-Resize-cjaMmsq8RGimOv5](https://user-images.githubusercontent.com/50074365/163768395-86feb9c2-3b5b-4945-8c36-54334d2bd05f.jpg)
![imgonline-com-ua-Resize-2glcVpW1T2U](https://user-images.githubusercontent.com/50074365/163735641-b582c682-041a-4bc0-8c7f-d8b68596eb5b.jpg)
![imgonline-com-ua-Resize-9h8EDo2D3bKe](https://user-images.githubusercontent.com/50074365/163735644-db6baf79-9147-4625-8820-5fc6bb1faefa.jpg)
![imgonline-com-ua-Resize-nQlQMuKkH41GCXxi](https://user-images.githubusercontent.com/50074365/163735779-59dd669e-1420-4cbd-8db4-029a0344364a.jpg)

Используется подход clean architecture с mvvm. 
Стек технологий:
* KTS
* Flow
* Livedata
* Coroutines
* Dagger
* Retrofit
* Paging
* Navigation component
* Picasso
* Firebase
* Timber

Попытался показать разделение на модули api и impl. В идеале можно было бы делить на feature модули и делать сабкомпоненты, но в данном проекте это излишне.
domain модуль пришлось сделать android-library, иначе не подключалась пагинация :(

Вынес все зависимости в buildSrc. Опять же излишне, но полезно для обновления версий

### TODO():
* Использовать обертку над запросами, чтобы получать sealed классы с ивентами вместо обычного Result
* Вынести di в отдельный модуль
* Спрятать storePassword и прочее) (не успел нормально сделать)
* Тесты
