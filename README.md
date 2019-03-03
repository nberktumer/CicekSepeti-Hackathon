# CicekSepeti Hackaton Entering Question Mobile App

- Android app for the ciceksepeti question asked before the hackathon
- Uses google maps sdk to display the stores and jobs in the map in correct locations
- Uses integer linear programming to solve the given problem, finds exact solution in approx 100ms in 6-core i7 processor, 500ms in an Android phone
  - Uses the ojalgo library to solve the integer programming problem with the constraints we have created based on the given question
- Follows MVVMI structure in the android part (Model-View-ViewModel-Interactor(domain layer))
- Can be directly tested on any android phone that has google play services and has sdk level >= 21, the app can be build using android studio
  
## License

Copyright 2018-now The Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this repository except in compliance with the License.
You may obtain a copy of the License at

- [Apache Website](http://www.apache.org/licenses/LICENSE-2.0)
- [LICENSE](./LICENSE) file

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
