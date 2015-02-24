var fs = require('fs');

var event_path 		= "./events.json";
var user_path 		= "./users.json";
var sport_path 		= "./sports.json";
var location_path 	= "./locations.json";


//returns specified event
exports.getEvent = function(name, res) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
			res.write(null);
			res.end();
			return;
		} else {
			var event_list = JSON.parse(events.toString()).events;
			for (var i = 0; i < event_list.length; i++) {
				if (event_list[i].name == name) {
					console.log("found event " + name);
					console.log(JSON.stringify(event_list[i]));
					res.write(JSON.stringify(event_list[i]));
					res.end();
					return;
				}
			}
			console.log("couldn't find event " + name);
			res.write(null);
			return;
		}
		res.end();
		return;
	});
}

//returns all events
exports.getAllEvents = function(res) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
			res.write(null);
		} else {
			var event_list = JSON.parse(events.toString()).events;
			res.write(JSON.stringify(event_list));
			console.log(JSON.stringify(event_list));
		}
		res.end();
		return;
	});
}

// adds specified event
exports.addEvent = function(event) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
		} else {
			console.log(JSON.stringify(event));
			var event_list = JSON.parse(events.toString()).events;
			event_list.push(event);
			fs.writeFile(event_path, 
				"{\"events\":" + JSON.stringify(event_list, null, 4) +"}", 
				function(err) {
					if (err) {
						console.log("Error while writing file");
					} else {
						console.log("Event written to " + event_path);
					}
			});
		}
		return;
	});
}

//deletes specified event
exports.deleteEvent = function(name) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
			return;
		} else {
			var event_list = JSON.parse(events.toString()).events;
			for (var i = 0; i < event_list.length; i++) {
				if (event_list[i].name == name) {
					console.log("found event " + name);
					event_list.splice(i,1)
					fs.writeFile(event_path, 
						"{\"events\":" + JSON.stringify(event_list, 
						null, 4) +"}", function(err) {
							if (err) {
								console.log("Error while writing file");
							} else {
								console.log("Event deleted from " + event_path);
							}
					});
					return;
				}
			}
			console.log("couldn't find event " + name);
			return;
		}
		return;
	});
}

//returns specified user
exports.getUser = function(username, res) {
	fs.readFile(user_path, function(err, users) {
		if (err) {
			console.log("error while reading " + user_path);
			res.write(null);
			res.end();
			return;
		} else {
			var user_list = JSON.parse(users.toString()).users;
			for (var i = 0; i < user_list.length; i++) {
				if (user_list[i].username == username) {
					console.log("found it");
					console.log(JSON.stringify(user_list[i]));
					res.write(JSON.stringify(user_list[i]));
					res.end();
					return;
				}
			}
			console.log("couldn't find user " + username);
			res.write(null);
			return;
		}
		console.log("res Ending");
		res.end();
		return;
	});
}

//adds specified user
exports.addUser = function(user) {
	fs.readFile(user_path, function(err, users) {
		if (err) {
			console.log("error while reading " + user_path);
		} else {
			console.log(JSON.stringify(user));
			var user_list = JSON.parse(users.toString()).users;
			user_list.push(user);
			fs.writeFile(user_path, 
				"{\"users\":" + JSON.stringify(user_list, null, 4) +"}", 
				function(err) {
					if (err) {
						console.log("Error while writing file");
					} else {
						console.log("Event written to " + user_path);
					}
			});
		}
		return;
	});
}

//deletes specified user
exports.deleteUser = function(name, res) {
	fs.readFile(user_path, function(err, users) {
		if (err) {
			console.log("error while reading " + user_path);
			res.write(null);
			res.end();
			return;
		} else {
			var user_list = JSON.parse(users.toString()).users;
			for (var i = 0; i < user_list.length; i++) {
				if (user_list[i].username == username) {
					console.log("found it");
					user_list.splice(i,1);
					fs.writeFile(user_path, 
						"{\"users\":" + JSON.stringify(user_list, null, 4) +"}", 
						function(err) {
							if (err) {
								console.log("Error while writing file");
							} else {
								console.log("Event written to " + user_path);
							}
					});
					return;
				}
			}
			console.log("couldn't find user " + name);
			return;
		}
		return;
	});
}

//returns specified sport
exports.getSport = function(sportName, res) {
	fs.readFile(sport_path, function(err, sports) {
		if (err) {
			console.log("error while reading " + sport_path);
			res.write(null);
			res.end();
			return;
		} else {
			var sport_list = JSON.parse(sports.toString()).sports;
			for (var i = 0; i < sport_list.length; i++) {
				if (sport_list[i].sportName == sportName) {
					console.log("found it");
					console.log(JSON.stringify(sport_list[i]));
					res.write(JSON.stringify(sport_list[i]));
					res.end();
					return;
				}
			}
			console.log("couldn't find sport " + sportName);
			res.write(null);
			return;
		}
		console.log("res Ending");
		res.end();
		return;
	});
}

//adds specified sport
exports.addSport = function(sport) {
	fs.readFile(sport_path, function(err, sports) {
		if (err) {
			console.log("error while reading " + sport_path);
		} else {
			console.log(JSON.stringify(sport));
			var sport_list = JSON.parse(sports.toString()).sports;
			sport_list.push(sport);
			fs.writeFile(sport_path, 
				"{\"sports\":" + JSON.stringify(sport_list, null, 4) +"}", 
				function(err) {
					if (err) {
						console.log("Error while writing file");
					} else {
						console.log("Event written to " + sport_path);
					}
			});
		}
		return;
	});
}

//deletes specified sport
exports.deleteSport = function(sport, res) {
	fs.readFile(sport_path, function(err, sports) {
		if (err) {
			console.log("error while reading " + sport_path);
			res.write(null);
			res.end();
			return;
		} else {
			var sport_list = JSON.parse(sports.toString()).sports;
			for (var i = 0; i < sport_list.length; i++) {
				if (sport_list[i].sportName == sport) {
					console.log("found it");
					sport_list.splice(i,1);
					fs.writeFile(sport_path, 
						"{\"sports\":" + JSON.stringify(sport_list, null, 4) +"}", 
						function(err) {
							if (err) {
								console.log("Error while writing file");
							} else {
								console.log("Sport written to " + sport_path);
							}
					});
					return;
				}
			}
			console.log("couldn't find sport " + sport);
			return;
		}
		return;
	});
}

//returns specified location
exports.getLocation = function(locationName, res) {
	fs.readFile(location_path, function(err, locations) {
		if (err) {
			console.log("error while reading " + location_path);
			res.write(null);
			res.end();
			return;
		} else {
			var location_list = JSON.parse(locations.toString()).locations;
			for (var i = 0; i < location_list.length; i++) {
				if (location_list[i].locationName == locationName) {
					console.log("found it");
					console.log(JSON.stringify(location_list[i]));
					res.write(JSON.stringify(location_list[i]));
					res.end();
					return;
				}
			}
			console.log("couldn't find location " + locationName);
			res.write(null);
			return;
		}
		console.log("res Ending");
		res.end();
		return;
	});
}

//adds specified location
exports.addLocation = function(location) {
	fs.readFile(location_path, function(err, locations) {
		if (err) {
			console.log("error while reading " + location_path);
		} else {
			console.log(JSON.stringify(location));
			var location_list = JSON.parse(locations.toString()).locations;
			location_list.push(location);
			fs.writeFile(location_path, 
				"{\"locations\":" + JSON.stringify(location_list, null, 4) +"}", 
				function(err) {
					if (err) {
						console.log("Error while writing file");
					} else {
						console.log("Event written to " + location_path);
					}
			});
		}
		return;
	});
}

//deletes specified location
exports.deleteLocation = function(location, res) {
	fs.readFile(location_path, function(err, locations) {
		if (err) {
			console.log("error while reading " + location_path);
			res.write(null);
			res.end();
			return;
		} else {
			var location_list = JSON.parse(locations.toString()).locations;
			for (var i = 0; i < location_list.length; i++) {
				if (location_list[i].location == location) {
					console.log("found it");
					location_list.splice(i,1);
					fs.writeFile(location_path, 
						"{\"locations\":" + JSON.stringify(location_list, null, 4) +"}", 
						function(err) {
							if (err) {
								console.log("Error while writing file");
							} else {
								console.log("location written to " + location_path);
							}
					});
					return;
				}
			}
			console.log("couldn't find location " + location);
			return;
		}
		return;
	});
}