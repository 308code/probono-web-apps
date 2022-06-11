FROM mongo:5.0

COPY init.json /init.json
CMD mongoimport --host mongodb --db probono --collection camphire_drilling_wells --type json --file /init.json --jsonArray