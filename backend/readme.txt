- state_api.py (zjišování stavu parkovištì)
	- vytvoøit api gateway pøi vytváøení lamda funkce
	- funkci nazvat place, stage hacking, gateway place
	- method by mìlo být any
	- mìlo by po requestu pøes web browser vracet
	- [{"available": false, "place_id": 3}, {"available": false, "place_id": 2}, {"available": false, "place_id": 1}]

- state_update.api.py (update stavu parkovištì)
	- vytvoøit pouze lambda funkci
	- funkci nazvat place_state
	- naimportovat api ze swagger filu pøes API Gateway management (jinak to v javì bude na input stream vracet error)
	- vytvoží se api chytre parkoviste s /place a fukcemi get a post
	- get odebrat z lambda funkce place a post pøiøadit k funkci place state
	- mìlo by to jít buï z API Gateway managementu kde se v resource vybere post -> integration request a pak se nastaví funkce
	- nebo z triggerù lambda funkce
	- po requestu z web broseru vrací json s count : 1

- state_journal_api - SE NEVYUŽÍVÁ

- db (DynamoDB)
	- vytvoøí se tabulky podle souboru
	- do carmac_map a placemac_map se vloží macovky aut a míst
	- pa_cur_state se vloží jednotlivá místa s available true

- práva pro funkce se použijí role lambda-parking (jaká práva ví KARM)
- ještì tam jsou nìkde práva pro roli na úpravu tabulek (to ví taky KARM)
