- state_api.py (zji��ov�n� stavu parkovi�t�)
	- vytvo�it api gateway p�i vytv��en� lamda funkce
	- funkci nazvat place, stage hacking, gateway place
	- method by m�lo b�t any
	- m�lo by po requestu p�es web browser vracet
	- [{"available": false, "place_id": 3}, {"available": false, "place_id": 2}, {"available": false, "place_id": 1}]

- state_update.api.py (update stavu parkovi�t�)
	- vytvo�it pouze lambda funkci
	- funkci nazvat place_state
	- naimportovat api ze swagger filu p�es API Gateway management (jinak to v jav� bude na input stream vracet error)
	- vytvo�� se api chytre parkoviste s /place a fukcemi get a post
	- get odebrat z lambda funkce place a post p�i�adit k funkci place state
	- m�lo by to j�t bu� z API Gateway managementu kde se v resource vybere post -> integration request a pak se nastav� funkce
	- nebo z trigger� lambda funkce
	- po requestu z web broseru vrac� json s count : 1

- state_journal_api - SE NEVYU��V�

- db (DynamoDB)
	- vytvo�� se tabulky podle souboru
	- do carmac_map a placemac_map se vlo�� macovky aut a m�st
	- pa_cur_state se vlo�� jednotliv� m�sta s available true

- pr�va pro funkce se pou�ij� role lambda-parking (jak� pr�va v� KARM)
- je�t� tam jsou n�kde pr�va pro roli na �pravu tabulek (to v� taky KARM)
